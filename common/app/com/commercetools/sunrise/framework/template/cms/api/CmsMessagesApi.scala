package com.commercetools.sunrise.framework.template.cms.api

import javax.inject.{Inject, Singleton}

import com.commercetools.sunrise.framework.template.i18n.YamlMessagesApi
import play.api.i18n._
import play.api.inject.Module
import play.api.{Configuration, Environment}

trait CmsMessagesApi extends MessagesApi {

}

@Singleton
class DefaultCmsMessagesApi @Inject()(environment: Environment, configuration: Configuration, langs: Langs)
  extends YamlMessagesApi(environment, configuration, langs) with CmsMessagesApi {

  override protected def loadAllMessages: Map[String, Map[String, String]] = {
    val filename = "cms"
    langs.availables.map(_.code).map { lang =>
      (lang, loadMessages(s"$filename.$lang"))
    }.toMap.+("default" -> loadMessages(filename))
  }
}

class CmsModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    Seq(
      bind[CmsMessagesApi].to[DefaultCmsMessagesApi]
    )
  }
}


