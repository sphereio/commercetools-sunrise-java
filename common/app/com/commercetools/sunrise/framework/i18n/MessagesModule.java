package com.commercetools.sunrise.framework.i18n;

import com.commercetools.sunrise.framework.i18n.api.SunriseLangs;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.google.inject.AbstractModule;
import play.api.i18n.Langs;

import javax.inject.Singleton;
import java.util.Locale;

public final class MessagesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Langs.class)
                .to(SunriseLangs.class)
                .in(Singleton.class);

        bind(play.i18n.MessagesApi.class)
                .to(com.commercetools.sunrise.framework.i18n.SunriseMessagesApi.class)
                .in(Singleton.class);

        bind(play.api.i18n.MessagesApi.class)
                .to(com.commercetools.sunrise.framework.i18n.api.SunriseMessagesApi.class)
                .in(Singleton.class);

        bind(Locale.class)
                .toProvider(LocaleFromRequestProvider.class)
                .in(RequestScoped.class);
    }
}
