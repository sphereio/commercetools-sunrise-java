package com.commercetools.sunrise.common.template.engine.handlebars;

import com.commercetools.sunrise.common.template.engine.TemplateContext;
import com.commercetools.sunrise.common.utils.ErrorFormatter;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.ValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import io.sphere.sdk.models.Base;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.common.template.engine.handlebars.HandlebarsCmsHelper.CMS_PAGE_IN_CONTEXT_KEY;
import static com.commercetools.sunrise.common.template.engine.handlebars.HandlebarsI18nHelper.LANGUAGE_TAGS_IN_CONTEXT_KEY;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class HandlebarsContextFactory extends Base {

    @Inject
    private ErrorFormatter errorFormatter;

    public Context create(final Handlebars handlebars, final String templateName, final TemplateContext templateContext) {
        final List<ValueResolver> valueResolvers = valueResolvers(templateContext);
        final Context context = Context.newBuilder(templateContext.pageData())
                .resolver(valueResolvers.toArray(new ValueResolver[valueResolvers.size()]))
                .build();
        fillContext(context, templateContext);
        return context;
    }

    protected void fillContext(final Context context, final TemplateContext templateContext) {
        fillLocale(context, templateContext);
        fillCmsPage(context, templateContext);
    }

    protected void fillCmsPage(final Context context, final TemplateContext templateContext) {
        templateContext.cmsPage()
                .ifPresent(cmsPage -> context.data(CMS_PAGE_IN_CONTEXT_KEY, cmsPage));
    }

    protected void fillLocale(final Context context, final TemplateContext templateContext) {
        context.data(LANGUAGE_TAGS_IN_CONTEXT_KEY, templateContext.locales().stream()
                .map(Locale::toLanguageTag)
                .collect(toList()));
    }

    protected List<ValueResolver> valueResolvers(final TemplateContext templateContext) {
        final PlayJavaFormResolver playJavaFormResolver = createPlayJavaFormResolver(templateContext);
        return asList(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE, playJavaFormResolver);
    }

    protected PlayJavaFormResolver createPlayJavaFormResolver(final TemplateContext templateContext) {
        return new PlayJavaFormResolver(templateContext.locales(), errorFormatter);
    }
}
