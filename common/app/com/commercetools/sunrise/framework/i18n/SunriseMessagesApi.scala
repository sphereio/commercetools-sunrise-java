package com.commercetools.sunrise.framework.i18n

import java.net.URL
import java.util.Map.Entry
import javax.inject.{Inject, Provider, Singleton}

import com.google.inject.ProvisionException
import io.sphere.sdk.projects.Project
import play.api.i18n._
import play.api.{Configuration, Environment, Logger}
import play.ext.i18n.MessagesLoader
import play.ext.i18n.MessagesLoaders.YamlFileLoader
import play.utils.Resources

@Singleton
class SunriseMessagesApi @Inject()(environment: Environment, configuration: Configuration, langs: Langs) extends DefaultMessagesApi(environment, configuration, langs) {

  protected lazy val overridePath: Option[String] = configuration.getString("play.i18n.overridePath")

  override protected def loadMessages(file: String): Map[String, String] = {

    def loadMessages(file: String, loader: MessagesLoader): Map[String, String] = {
      import scala.collection.JavaConverters._

      def loadFiles(path: Option[String]) = {
        def joinPaths(first: Option[String], second: String) = first match {
          case Some(parent) => new java.io.File(parent, second).getPath
          case None => second
        }
        environment.classLoader.getResources(joinPaths(path, file)).asScala.toList
      }

      def parseFile(messageFile: URL): Map[String, String] = loader(Messages.UrlMessageSource(messageFile), messageFile.toString).fold(e => throw e, identity)

      def overridenFiles = if (overridePath.isDefined) loadFiles(overridePath) else List()

      (overridenFiles ++ loadFiles(messagesPrefix))
        .filterNot(Resources.isDirectory(environment.classLoader, _))
        .reverse
        .map(parseFile)
        .foldLeft(Map.empty[String, String]) { _ ++ _ }
    }

    loadMessages(s"$file.yaml", YamlFileLoader)
  }

  override def translate(key: String, args: Seq[Any])(implicit lang: Lang): Option[String] = {

    def filterHashArgsEntries(args: Seq[Any]): Seq[Entry[String, Any]] = {
      def isHashArgsEntry(entry: Any) = entry.isInstanceOf[Entry[_, _]] && entry.asInstanceOf[Entry[_, _]].getKey.isInstanceOf[String]
      args.filter(isHashArgsEntry)
        .map(_.asInstanceOf[Entry[String, Any]])
    }

    def generateAppliedKey(key: String, args: Seq[Entry[String, Any]]): String = {
      def isPluralMessage(args: Seq[Entry[String, Any]]) = {
        args.filter(_.getKey == "count")
          .map(_.getValue)
          .filter(_.isInstanceOf[Number])
          .exists(_.asInstanceOf[Number].doubleValue != 1)
      }
      def pluralizeKey(key: String): String = key + "_plural"
      if (isPluralMessage(args)) pluralizeKey(key) else key
    }

    def replaceParameters(pattern: String, args: Seq[Entry[String, Any]]): String = {
      args.filter(_.getValue != null)
        .foldLeft(pattern)((acc, entry) => acc.replace("__" + entry.getKey + "__", entry.getValue.toString))
    }

    val codesToTry = Seq(lang.code, lang.language, "default", "default.play")
    val hashArgs = filterHashArgsEntries(args)
    val appliedKey = generateAppliedKey(key, hashArgs)
    val pattern: Option[String] = codesToTry.foldLeft[Option[String]](None)((res, code) =>
      res.orElse(messages.get(code).flatMap(_.get(appliedKey))))
    pattern.map(pattern => replaceParameters(pattern, hashArgs))
  }
}

@Singleton
class SunriseLangs @Inject()(configuration: Configuration, projectProvider: Provider[Project]) extends DefaultLangs(configuration) {
  import scala.collection.JavaConversions._

  override val availables: Seq[Lang] = configuredLangs match {
    case None | Some(Nil) => fallbackLangs
    case Some(langs) => langs.map(Lang.apply)
  }

  lazy val fallbackLangs: Seq[Lang] = loadFallbackLangs

  private lazy val systemDefaultLangs = Seq(Lang.defaultLang)

  def configuredLangs: Option[Seq[String]] = {
    configuration.getStringSeq("play.i18n.langs").orElse {
      configuration.getStringSeq("sunrise.ctp.project.languages")
    }
  }

  def loadFallbackLangs: Seq[Lang] = {
    try {
      val projectLangs = projectProvider.get.getLanguages.map(Lang.apply)
      if (projectLangs.isEmpty) systemDefaultLangs else projectLangs
    } catch {
      case pe: ProvisionException =>
        Logger.warn("Languages from CTP could not be provided, falling back to default locale")
        systemDefaultLangs
    }
  }
}
