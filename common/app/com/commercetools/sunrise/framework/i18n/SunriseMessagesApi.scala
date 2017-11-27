package com.commercetools.sunrise.framework.i18n

import java.net.URL
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

    def filterHashArgsEntries(args: Seq[Any]): Seq[(String, Any)] = {
      def isHashArgsEntry(entry: Any) = entry.isInstanceOf[(_, _)] && entry.asInstanceOf[(_, _)]._1.isInstanceOf[String]
      args.filter(isHashArgsEntry)
        .map(_.asInstanceOf[(String, Any)])
    }

    def generateSpecificKey(key: String, args: Seq[(String, Any)]): String = {
      def isPluralMessage(args: Seq[(String, Any)]) = {
        args.filter(_._1 == "count")
          .map(_._2)
          .filter(_.isInstanceOf[Number])
          .exists(_.asInstanceOf[Number].doubleValue != 1)
      }
      def pluralizeKey(key: String): String = key + "_plural"
      if (isPluralMessage(args)) pluralizeKey(key) else key
    }

    def replaceParameters(pattern: String, args: Seq[(String, Any)]): String = {
      args.filter(_._2 != null)
        .foldLeft(pattern)((acc, entry) => acc.replace("__" + entry._1 + "__", entry._2.toString))
    }

    val codesToTry = Seq(lang.code, lang.language, "default", "default.play")
    val hashArgs = filterHashArgsEntries(args)
    val specificKey = generateSpecificKey(key, hashArgs)
    val pattern: Option[String] = codesToTry.foldLeft[Option[String]](None)((res, code) =>
      res.orElse(messages.get(code).flatMap(codeMessages => codeMessages.get(specificKey).orElse(codeMessages.get(key)))))
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
    configuration.getStringSeq("sunrise.ctp.project.languages") map { langs =>
      Logger.warn("sunrise.ctp.project.languages is deprecated, use play.i18n.langs instead")
      langs
    } orElse {
      configuration.getStringSeq("play.i18n.langs")
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
