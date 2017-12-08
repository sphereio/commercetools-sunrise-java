package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Options;

import javax.inject.Inject;
import java.util.Optional;

import static com.commercetools.sunrise.framework.template.engine.handlebars.HandlebarsTemplateEngine.CMS_PAGE_IN_CONTEXT_KEY;

public class DefaultHandlebarsHelperSource implements HandlebarsHelperSource {

    private final MessagesResolver messagesResolver;

    @Inject
    protected DefaultHandlebarsHelperSource(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    /**
     * Registrable {@code i18n} helper to obtain the correct translation for the given {@code messageKey}.
     * @param messageKey the message key to translate
     * @param options available options
     * @return the translated message
     */
    public CharSequence i18n(final String messageKey, final Options options) {
        return messagesResolver.getOrEmpty(messageKey, options.hash);
    }

    /**
     * Registrable {@code cms} helper to obtain the CMS content for the given {@code fieldName}.
     * @param fieldName the field name corresponding to the content
     * @param options available options
     * @return the content
     */
    public CharSequence cms(final String fieldName, final Options options) {
        final Optional<CmsPage> cmsPage = getCmsPageFromContext(options.context);
        return cmsPage.flatMap(page -> page.field(fieldName)).orElse("");
    }

    /**
     * Registrable {@code json} helper to obtain the JSON string representation of a Java object.
     * @param object the Java object
     * @return the JSON string representation
     * @throws JsonProcessingException if the given object could not be processed into JSON
     */
    public CharSequence json(final Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private static Optional<CmsPage> getCmsPageFromContext(final Context context) {
        final Object cmsPageAsObject = context.get(CMS_PAGE_IN_CONTEXT_KEY);
        if (cmsPageAsObject instanceof CmsPage) {
            return Optional.of((CmsPage) cmsPageAsObject);
        } else {
            return Optional.empty();
        }
    }
}
