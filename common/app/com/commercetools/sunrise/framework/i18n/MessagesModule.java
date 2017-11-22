package com.commercetools.sunrise.framework.i18n;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.google.inject.AbstractModule;
import play.api.i18n.Langs;
import play.api.i18n.MessagesApi;

import javax.inject.Singleton;
import java.util.Locale;

public final class MessagesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Langs.class)
                .to(SunriseLangs.class)
                .in(Singleton.class);

        bind(MessagesApi.class)
                .to(SunriseMessagesApi.class)
                .in(Singleton.class);

        bind(Locale.class)
                .toProvider(LocaleFromUrlProvider.class)
                .in(RequestScoped.class);
    }
}
