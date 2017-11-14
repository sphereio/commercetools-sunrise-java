package play.ext.i18n

import java.util.Map.Entry
import javax.inject.{Inject, Singleton}

import play.api.i18n._
import play.api.inject.Module
import play.api.{Configuration, Environment}
import play.ext.i18n.MessagesLoaders.YamlFileLoader
import play.utils.Resources

@Singleton
class YamlMessagesApi @Inject()(environment: Environment, configuration: Configuration, langs: Langs) extends DefaultMessagesApi(environment, configuration, langs) {

//  private val config = PlayConfig(configuration)
//  protected val fileNames: Seq[String] = config.get[Seq[String]]("play.i18n.files")


  override protected def loadMessages(file: String): Map[String, String] = {

    def loadMessages(file: String, loader: MessagesLoader): Map[String, String] = {
      import scala.collection.JavaConverters._

      def joinPaths(first: Option[String], second: String) = first match {
        case Some(parent) => new java.io.File(parent, second).getPath
        case None => second
      }

      environment.classLoader.getResources(joinPaths(messagesPrefix, file)).asScala.toList
        .filterNot(url => Resources.isDirectory(environment.classLoader, url)).reverse
        .map { messageFile =>
          loader(Messages.UrlMessageSource(messageFile), messageFile.toString).fold(e => throw e, identity)
        }.foldLeft(Map.empty[String, String]) { _ ++ _ }
    }

    loadMessages(s"$file.yaml", YamlFileLoader)
  }

  override def translate(key: String, args: Seq[Any])(implicit lang: Lang): Option[String] = {

    def filterHashArgsEntries(args: Seq[Any]): Seq[Entry[String, Any]] = {
      def isHashArgsEntry(entry: Any) = entry.isInstanceOf[Entry[Any, Any]] && entry.asInstanceOf[Entry[Any, Any]].getKey.isInstanceOf[String]
      args.filter(isHashArgsEntry)
        .map(_.asInstanceOf[Entry[String, Any]])
    }

    def findAppliedKey(key: String, args: Seq[Entry[String, Any]]): String = {
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
    val appliedKey = findAppliedKey(key, hashArgs)
    val pattern: Option[String] = codesToTry.foldLeft[Option[String]](None)((res, code) =>
      res.orElse(messages.get(code).flatMap(_.get(appliedKey))))
    pattern.map(pattern => replaceParameters(pattern, hashArgs))
  }

  // CMS messages API only: loadAllMessages, adding "filenames"
}

class MessagesModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    Seq(
      bind[Langs].to[DefaultLangs],
      bind[MessagesApi].to[YamlMessagesApi]
    )
  }
}
