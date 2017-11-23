package com.commercetools.sunrise.framework.cms

import javax.inject.{Inject, Singleton}

import com.commercetools.sunrise.framework.i18n.SunriseMessagesApi
import play.api.i18n._
import play.api.{Configuration, Environment}

@Singleton
private[cms] class DefaultCmsMessagesApi @Inject()(environment: Environment, configuration: Configuration, langs: Langs) extends SunriseMessagesApi(environment, configuration, langs) {

  private val filename = "cms"

  override protected def loadAllMessages: Map[String, Map[String, String]] = {
    langs.availables.map(_.code).map { lang =>
      (lang, loadMessages(s"$filename.$lang"))
    }.toMap
      .+("default" -> loadMessages(filename))
  }
}


