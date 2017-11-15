package com.commercetools.sunrise.framework.template;

import com.commercetools.sunrise.framework.template.engine.HandlebarsTemplateEngineProvider;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;

/**
 * Module that allows to inject theme related classes, such as CMS, i18n Resolver and Template Engine.
 */
public final class ThemeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TemplateEngine.class)
                .toProvider(HandlebarsTemplateEngineProvider.class)
                .in(Singleton.class);
    }
}
