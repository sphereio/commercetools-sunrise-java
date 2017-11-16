package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.injection.RequestScoped;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
final class PageTitleResolverImpl implements PageTitleResolver {

    private final MessagesResolver messagesResolver;

    @Inject
    PageTitleResolverImpl(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    @Override
    public Optional<String> find(final String key) {
        return messagesResolver.get(key);
    }
}
