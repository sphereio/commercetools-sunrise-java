package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
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
