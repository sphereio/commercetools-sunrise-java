package com.commercetools.sunrise.framework.i18n;

import play.i18n.Langs;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Locale;

/**
 * Provides the {@link Locale} instance extracted from the URL using {@code languageTag} as the route variable name.
 */
@Singleton
public final class LocaleFromUrlProvider implements Provider<Locale> {

    private final Langs langs;

    @Inject
    LocaleFromUrlProvider(final Langs langs) {
        this.langs = langs;
    }

    @Override
    public Locale get() {
        final Http.Context httpContext = Http.Context.current.get();
        if (httpContext != null) {
            return httpContext.lang().toLocale();
        } else {
            return langs.preferred(langs.availables()).toLocale();
        }
    }
}