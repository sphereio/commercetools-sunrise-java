package play.ext.i18n

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
    loadMessages(s"$file.yaml", YamlFileLoader)
  }

  private def loadMessages(file: String, loader: MessagesLoader): Map[String, String] = {
    import scala.collection.JavaConverters._

    environment.classLoader.getResources(joinPaths(messagesPrefix, file)).asScala.toList
      .filterNot(url => Resources.isDirectory(environment.classLoader, url)).reverse
      .map { messageFile =>
        loader(Messages.UrlMessageSource(messageFile), messageFile.toString).fold(e => throw e, identity)
      }.foldLeft(Map.empty[String, String]) { _ ++ _ }
  }

  private def joinPaths(first: Option[String], second: String) = first match {
    case Some(parent) => new java.io.File(parent, second).getPath
    case None => second
  }

  override def translate(key: String, args: Seq[Any])(implicit lang: Lang): Option[String] = args match {
    case hashArgs: Map[String, Any] =>
      val codesToTry = Seq(lang.code, lang.language, "default", "default.play")
      val pluralizedKey = pluralizeKey(key, hashArgs)
      val pattern: Option[String] = codesToTry.foldLeft[Option[String]](None)((res, code) =>
        res.orElse(messages.get(code).flatMap(_.get(pluralizedKey))))
      pattern.map(pattern => replaceParameters(pattern, hashArgs))
    case _ => super.translate(key, args)
  }

  private def pluralizeKey(key: String, args: Map[String, Any]) = if (isPluralMessage(args)) key + "_plural" else key

  private def isPluralMessage(args: Map[String, Any]) = {
    args.iterator
      .filter(_._1 == "count")
      .filter(_._2.isInstanceOf[Number])
      .exists(_._2.asInstanceOf[Number].doubleValue != 1)
  }

  private def replaceParameters(pattern: String, args: Map[String, Any]): String = {
    args.iterator
      .filter(entry => entry._2 != null)
      .foldLeft(pattern)((acc, entry) => acc.replace("__" + entry._1 + "__", entry._2.toString))
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
