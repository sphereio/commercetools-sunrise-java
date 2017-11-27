package com.commercetools.sunrise.framework.i18n

import java.io.File

import org.scalatestplus.play.PlaySpec
import play.api.i18n.{DefaultLangs, Lang}
import play.api.{Configuration, Environment, Mode, PlayException}

class SunriseMessagesApiSpec extends PlaySpec {

  "SunriseMessagesApi translation" should {

    val api: SunriseMessagesApi = new SunriseMessagesApi(new Environment(new File("."), this.getClass.getClassLoader, Mode.Dev),
      Configuration.reference, new DefaultLangs(Configuration.reference ++ Configuration.from(Map(
        "play.i18n.langs" -> Seq("en", "fr", "fr-CH"))))) {
      override protected def loadAllMessages: Map[String, Map[String, String]] = Map(
        "default" -> Map(
          "title" -> "English Title",
          "foo" -> "English foo",
          "bar" -> "English pub",
          "args" -> "Hello __firstName__ __lastName__!",
          "count" -> "__count__ item",
          "count_plural" -> "__count__ items",
          "noplural" -> "__count__ items"),
        "fr" -> Map(
          "title" -> "Titre francais",
          "foo" -> "foo francais"),
        "fr-CH" -> Map(
          "title" -> "Titre suisse"))}

    def translate(msg: String, lang: String, reg: String, args: Seq[Any] = Seq()): Option[String] = {
      api.translate(msg, args)(Lang(lang, reg))
    }

    def isDefinedAt(msg: String, lang: String, reg: String): Boolean =
      api.isDefinedAt(msg)(Lang(lang, reg))

    "fall back to less specific translation" in {
      // Direct lookups
      translate("title", "fr", "CH") mustBe Some("Titre suisse")
      translate("title", "fr", "") mustBe Some("Titre francais")
      isDefinedAt("title", "fr", "CH") mustBe true
      isDefinedAt("title", "fr", "") mustBe true

      // Region that is missing
      translate("title", "fr", "FR") mustBe Some("Titre francais")
      isDefinedAt("title", "fr", "FR") mustBe true

      // Translation missing in the given region
      translate("foo", "fr", "CH") mustBe Some("foo francais")
      translate("bar", "fr", "CH") mustBe Some("English pub")
      isDefinedAt("foo", "fr", "CH") mustBe true
      isDefinedAt("bar", "fr", "CH") mustBe true

      // Unrecognized language
      translate("title", "bo", "GO") mustBe Some("English Title")
      isDefinedAt("title", "bo", "GO") mustBe true

      // Missing translation
      translate("garbled", "fr", "CH") mustBe None
      isDefinedAt("garbled", "fr", "CH") mustBe false
    }

    "resolve argument substitution" in {
      translate("args", "en", "", Seq("firstName" -> "John", "lastName" -> "Doe")) mustBe Some("Hello John Doe!")
      translate("args", "en", "", Seq("lastName" -> "Doe")) mustBe Some("Hello __firstName__ Doe!")
      translate("args", "en", "") mustBe Some("Hello __firstName__ __lastName__!")
    }

    "resolve pluralization" in {
      translate("count", "en", "", Seq("count" -> 0)) mustBe Some("0 items")
      translate("count", "en", "", Seq("count" -> 1)) mustBe Some("1 item")
      translate("count", "en", "", Seq("count" -> 2)) mustBe Some("2 items")
      translate("count", "en", "", Seq("count" -> 10)) mustBe Some("10 items")

      // Missing pluralized message
      translate("noplural", "en", "", Seq("count" -> 1)) mustBe Some("1 items")
      translate("noplural", "en", "", Seq("count" -> 2)) mustBe Some("2 items")
    }
  }

  "report error for invalid lang" in {
    a[PlayException] must be thrownBy {
      new SunriseMessagesApi(new Environment(new File("."), this.getClass.getClassLoader, Mode.Dev),
        Configuration.reference, new DefaultLangs(Configuration.reference ++ Configuration.from(Map("play.i18n.langs" -> Seq("invalid_language")))))
    }
  }

  "SunriseMessagesApi message loader" should {

    val api: SunriseMessagesApi = new SunriseMessagesApi(new Environment(new File("."), this.getClass.getClassLoader, Mode.Dev),
      Configuration.reference ++ Configuration.from(Map(
        "play.i18n.path" -> "i18n",
        "play.i18n.overridePath" -> "i18n/override"
      )), new DefaultLangs(Configuration.reference ++ Configuration.from(Map(
        "play.i18n.langs" -> Seq("en", "de")))))

    def findMessage(code: String, key: String): Option[String] = api.messages.get(code) flatMap (_.get(key))

    "load localized YAML messages" in {
      findMessage("default", "foo.bar") mustBe Some("something")
      findMessage("de", "foo.bar") mustBe Some("etwas")
    }

    "override YAML messages" in {
      findMessage("default", "hello") mustBe Some("bye")
      findMessage("de", "hello") mustBe Some("tschüß")
    }
  }
}
