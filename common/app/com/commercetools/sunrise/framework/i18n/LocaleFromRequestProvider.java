package com.commercetools.sunrise.framework.i18n;

import play.i18n.Lang;
import play.i18n.Langs;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Provides the {@link Locale} instance from the language saved in context and the accepted languages from the user.
 */
@Singleton
public final class LocaleFromRequestProvider implements Provider<Locale> {

    private final Langs langs;

    @Inject
    LocaleFromRequestProvider(final Langs langs) {
        this.langs = langs;
    }

    @Override
    public Locale get() {
        return langs.preferred(candidates()).toLocale();
    }

    private static List<Lang> candidates() {
        return Optional.ofNullable(Http.Context.current.get())
                .map(context -> Stream.concat(Stream.of(context.lang()), context.request().acceptLanguages().stream()).collect(toList()))
                .orElseGet(Collections::emptyList);
    }
}