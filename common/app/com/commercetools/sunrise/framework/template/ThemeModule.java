package com.commercetools.sunrise.framework.template;

import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.template.cms.filebased.FileBasedCmsService;
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
        bind(CmsService.class)
                .to(FileBasedCmsService.class)
                .in(Singleton.class);

        bind(TemplateEngine.class)
                .toProvider(HandlebarsTemplateEngineProvider.class)
                .in(Singleton.class);

//        bind(I18nResolver.class)
//                .toProvider(MessagesApiI18nResolver.class)
//                .in(Singleton.class);
    }
}
