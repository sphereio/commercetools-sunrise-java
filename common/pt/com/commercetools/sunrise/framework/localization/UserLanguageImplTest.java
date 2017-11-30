package com.commercetools.sunrise.framework.localization;

import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.i18n.Lang;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.test.WithApplication;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Locale.*;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static play.mvc.Http.HeaderNames.ACCEPT_LANGUAGE;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.invokeWithContext;

public class UserLanguageImplTest extends WithApplication {

    private UserLanguage userLanguage;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure("play.i18n.langs", asList("en", "de", "en-US"))
                .build();
    }

    @Before
    public void setUp() throws Exception {
        userLanguage = app.injector().instanceOf(UserLanguageImpl.class);
    }

    @Test
    public void addsAcceptedLanguages() throws Exception {
        testWithHttpContext(ENGLISH, asList(GERMAN, US), () ->
                assertThat(userLanguage.locales()).containsExactly(ENGLISH, GERMAN, US));
    }

    @Test
    public void discardsDuplicated() throws Exception {
        testWithHttpContext(ENGLISH, asList(GERMAN, ENGLISH, US), () ->
                assertThat(userLanguage.locales()).containsExactly(ENGLISH, GERMAN, US));
    }

    @Test
    public void discardsNotSupportedByProject() throws Exception {
        testWithHttpContext(ENGLISH, asList(ITALIAN, US), () ->
                assertThat(userLanguage.locales()).containsExactly(ENGLISH, US));
    }

    @Test
    public void ignoresEmptyAcceptedLanguages() throws Exception {
        testWithHttpContext(ENGLISH, emptyList(), () ->
                assertThat(userLanguage.locales()).containsExactly(ENGLISH));
    }

    @Test
    public void ignoresNotSupportedCurrentLanguage() throws Exception {
        testWithHttpContext(ITALIAN, asList(GERMAN, US), () ->
                assertThat(userLanguage.locales()).containsExactly(GERMAN, US));
    }

    @Test
    public void doesNotFailOnHttpContextNotAvailable() throws Exception {
        assertThat(userLanguage.locales()).containsExactly(ENGLISH);
    }

    private void testWithHttpContext(final Locale currentLang, final List<Locale> acceptedLangs, final Runnable test) {
        final Http.RequestBuilder requestWithAcceptLanguage = fakeRequest()
                .header(ACCEPT_LANGUAGE, toAcceptLanguageHeader(acceptedLangs));
         invokeWithContext(requestWithAcceptLanguage, () -> {
            Http.Context.current().changeLang(Lang.forCode(currentLang.toLanguageTag()));
            test.run();
            return null;
        });
    }

    private static String toAcceptLanguageHeader(final List<Locale> acceptedLangs) {
        return acceptedLangs.stream()
                    .map(Locale::toLanguageTag)
                    .collect(Collectors.joining(","));
    }
}