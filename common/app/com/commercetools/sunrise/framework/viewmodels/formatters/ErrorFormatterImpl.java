package com.commercetools.sunrise.framework.viewmodels.formatters;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.injection.RequestScoped;

import javax.inject.Inject;

@RequestScoped
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
