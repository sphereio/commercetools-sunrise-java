package com.commercetools.sunrise.framework.i18n;

import play.i18n.Lang;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Singleton
final class MessagesResolverImpl implements MessagesResolver {

    private final MessagesApi messagesApi;
    private final Locale locale;

    @Inject
    MessagesResolverImpl(final MessagesApi messagesApi, final Locale locale) {
        this.messagesApi = messagesApi;
        this.locale = locale;
    }

    @Override
    public Optional<String> get(final Locale locale, final String messageKey, final Map<String, Object> args) {
        final Lang lang = new Lang(locale);
        final String key = transformDeprecatedKey(messageKey);
        return translate(lang, key, args);
    }

    @Override
    public Locale currentLanguage() {
        return locale;
    }

    private Optional<String> translate(final Lang lang, final String messageKey, final Map<String, Object> args) {
        if (messagesApi.isDefinedAt(lang, messageKey)) {
            return Optional.of(messagesApi.get(lang, messageKey, args.entrySet().toArray()));
        }
        return Optional.empty();
    }

    private String transformDeprecatedKey(final String messageKey) {
        return messageKey.replaceFirst(":", ".");
    }
}
