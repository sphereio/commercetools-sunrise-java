package com.commercetools.sunrise.framework.template.i18n;

import play.i18n.Lang;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Singleton
final class MessagesApiI18nResolver implements I18nResolver {

    private final MessagesApi messagesApi;

    @Inject
    MessagesApiI18nResolver(final MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Optional<String> get(final List<Locale> locales, final I18nIdentifier i18nIdentifier, final Map<String, Object> hashArgs) {
        final String messageKey = buildMessageKey(i18nIdentifier);
        return locales.stream()
                .map(locale -> translate(locale, messageKey, hashArgs))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<String> translate(final Locale locale, final String messageKey, final Map<String, Object> hashArgs) {
        final Lang lang = new Lang(locale);
        if (messagesApi.isDefinedAt(lang, messageKey)) {
            return Optional.of(messagesApi.get(lang, messageKey, hashArgs));
        }
        return Optional.empty();
    }

    private String buildMessageKey(final I18nIdentifier i18nIdentifier) {
        return String.format("%s.%s", i18nIdentifier.getBundle(), i18nIdentifier.getMessageKey());
    }
}
