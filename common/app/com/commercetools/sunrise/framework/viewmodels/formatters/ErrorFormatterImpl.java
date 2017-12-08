package com.commercetools.sunrise.framework.viewmodels.formatters;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ErrorFormatterImpl implements ErrorFormatter {

    private final MessagesResolver messagesResolver;

    @Inject
    ErrorFormatterImpl(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    @Override
    public String format(final String messageKey) {
        return messagesResolver.getOrKey(messageKey);
    }
}
