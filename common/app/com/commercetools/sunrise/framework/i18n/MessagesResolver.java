package com.commercetools.sunrise.framework.i18n;

import com.google.inject.ImplementedBy;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

@ImplementedBy(MessagesResolverImpl.class)
public interface MessagesResolver {

    /**
     * @return the current language
     */
    Locale currentLanguage();

    /**
     * Resolves i18n message for the given locale.
     * @param locale the locale used to translate the message
     * @param messageKey identifier of the i18n message
     * @param args list of arguments
     * @return the resolved message, or absent if it could not be found
     */
    Optional<String> get(final Locale locale, final String messageKey, final Map<String, Object> args);

    /**
     * Resolves i18n message for the given locale.
     * @param locale the locale used to translate the message
     * @param messageKey identifier of the i18n message
     * @return the resolved message, or absent if it could not be found
     */
    default Optional<String> get(final Locale locale, final String messageKey) {
        return get(locale, messageKey, emptyMap());
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @param args list of arguments
     * @return the resolved message, or absent if it could not be found
     */
    default Optional<String> get(final String messageKey, final Map<String, Object> args) {
        return get(currentLanguage(), messageKey, args);
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @return the resolved message, or absent if it could not be found
     */
    default Optional<String> get(final String messageKey) {
        return get(messageKey, emptyMap());
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @param args list of hash arguments
     * @return the resolved message, or empty string if it could not be found
     */
    default String getOrEmpty(final String messageKey, final Map<String, Object> args) {
        return get(messageKey, args).orElse("");
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @return the resolved message, or empty string if it could not be found
     */
    default String getOrEmpty(final String messageKey) {
        return get(messageKey).orElse("");
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @param args list of hash arguments
     * @return the resolved message, or the message key if it could not be found
     */
    default String getOrKey(final String messageKey, final Map<String, Object> args) {
        return get(messageKey, args).orElse(messageKey);
    }

    /**
     * Resolves i18n message for the current language.
     * @param messageKey identifier of the i18n message
     * @return the resolved message, or the message key if it could not be found
     */
    default String getOrKey(final String messageKey) {
        return get(messageKey).orElse(messageKey);
    }
}
