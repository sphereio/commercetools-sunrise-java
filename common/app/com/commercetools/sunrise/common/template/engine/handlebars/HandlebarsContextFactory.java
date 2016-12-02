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
        final Context context = createContextBuilder(templateContext).build();
        return createContext(context, templateContext);
    }

    protected final Context.Builder createContextBuilder(final TemplateContext templateContext) {
        final Context.Builder contextBuilder = Context.newBuilder(templateContext.pageData());
        return contextBuilderWithValueResolvers(contextBuilder, templateContext);
    }

    protected final Context createContext(final Context context, final TemplateContext templateContext) {
        final Context contextWithLocale = contextWithLocale(context, templateContext);
        return contextWithCmsPage(contextWithLocale, templateContext);
    }

    protected Context contextWithCmsPage(final Context context, final TemplateContext templateContext) {
        return templateContext.cmsPage()
                .map(cmsPage -> context.data(CMS_PAGE_IN_CONTEXT_KEY, cmsPage))
                .orElse(context);
    }

    protected Context contextWithLocale(final Context context, final TemplateContext templateContext) {
        return context.data(LANGUAGE_TAGS_IN_CONTEXT_KEY, templateContext.locales().stream()
                .map(Locale::toLanguageTag)
                .collect(toList()));
    }

    protected List<ValueResolver> valueResolvers(final TemplateContext templateContext) {
        final PlayJavaFormResolver playJavaFormResolver = createPlayJavaFormResolver(templateContext);
        return asList(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE, playJavaFormResolver);
    }

    protected final Context.Builder contextBuilderWithValueResolvers(final Context.Builder contextBuilder, final TemplateContext templateContext) {
        final List<ValueResolver> valueResolvers = valueResolvers(templateContext);
        return contextBuilder.resolver(valueResolvers.toArray(new ValueResolver[valueResolvers.size()]));
    }

    protected final PlayJavaFormResolver createPlayJavaFormResolver(final TemplateContext templateContext) {
        return new PlayJavaFormResolver(templateContext.locales(), errorFormatter);
    }
}
