package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.template.engine.TemplateContext;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.util.List;
import java.util.Locale;

final class TestableHandlebarsTemplateEngine implements TemplateEngine {

    private final TemplateEngine delegate;

    TestableHandlebarsTemplateEngine(final List<TemplateLoader> templateLoaders, final MessagesResolver messagesResolver) {
        this.delegate = handlebarsTemplateEngine(templateLoaders, messagesResolver);
    }

    @Override
    public String render(final String templateName, final TemplateContext templateContext) {
        return delegate.render(templateName, templateContext);
    }

    private TemplateEngine handlebarsTemplateEngine(final List<TemplateLoader> templateLoaders, final MessagesResolver messagesResolver) {
        final Handlebars handlebars = handlebarsFactory(messagesResolver).create(templateLoaders);
        return HandlebarsTemplateEngine.of(handlebars, handlebarsContextFactory());
    }

    private HandlebarsContextFactory handlebarsContextFactory() {
        final PlayJavaFormResolver playJavaFormResolver = new PlayJavaFormResolver(msg -> msg);
        final SunriseJavaBeanValueResolver sunriseJavaBeanValueResolver = new SunriseJavaBeanValueResolver(Locale.ENGLISH);
        return new HandlebarsContextFactory(playJavaFormResolver, sunriseJavaBeanValueResolver);
    }

    private static HandlebarsFactory handlebarsFactory(final MessagesResolver messagesResolver) {
        final I18nHandlebarsHelper i18nHandlebarsHelper = new I18nHandlebarsHelperImpl(messagesResolver);
        final CmsHandlebarsHelper cmsHandlebarsHelper = new CmsHandlebarsHelperImpl();
        return new HandlebarsFactory(i18nHandlebarsHelper, cmsHandlebarsHelper);
    }
}
