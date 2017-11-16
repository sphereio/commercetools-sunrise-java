package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.github.jknack.handlebars.Options;

import javax.inject.Inject;
import java.io.IOException;

final class I18nHandlebarsHelperImpl implements I18nHandlebarsHelper {

    private final MessagesResolver messagesResolver;

    @Inject
    I18nHandlebarsHelperImpl(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    @Override
    public CharSequence apply(final String context, final Options options) throws IOException {
        return messagesResolver.getOrEmpty(context, options.hash);
    }
}
