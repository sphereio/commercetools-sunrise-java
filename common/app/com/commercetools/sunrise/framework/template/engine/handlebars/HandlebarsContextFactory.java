package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.framework.template.engine.TemplateContext;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.ValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;

import javax.inject.Inject;
import java.util.List;

import static java.util.Arrays.asList;

public class HandlebarsContextFactory {

    private final PlayJavaFormResolver playJavaFormResolver;
    private final SunriseJavaBeanValueResolver sunriseJavaBeanValueResolver;

    @Inject
    protected HandlebarsContextFactory(final PlayJavaFormResolver playJavaFormResolver,
                                       final SunriseJavaBeanValueResolver sunriseJavaBeanValueResolver) {
        this.playJavaFormResolver = playJavaFormResolver;
        this.sunriseJavaBeanValueResolver = sunriseJavaBeanValueResolver;
    }

    public Context create(final Handlebars handlebars, final String templateName, final TemplateContext templateContext) {
        final Context.Builder contextBuilder = Context.newBuilder(templateContext.pageData());
        final Context context = buildContextBuilder(contextBuilder, templateContext).build();
        return contextWithCmsPage(context, templateContext);
    }

    protected List<ValueResolver> valueResolvers() {
        return asList(MapValueResolver.INSTANCE, sunriseJavaBeanValueResolver, playJavaFormResolver);
    }

    protected Context.Builder buildContextBuilder(final Context.Builder contextBuilder, final TemplateContext templateContext) {
        final List<ValueResolver> valueResolvers = valueResolvers();
        return contextBuilder.resolver(valueResolvers.toArray(new ValueResolver[valueResolvers.size()]));
    }

    protected final Context contextWithCmsPage(final Context context, final TemplateContext templateContext) {
        return templateContext.cmsPage()
                .map(cmsPage -> context.data(CmsHandlebarsHelper.CMS_PAGE_IN_CONTEXT_KEY, cmsPage))
                .orElse(context);
    }
}
