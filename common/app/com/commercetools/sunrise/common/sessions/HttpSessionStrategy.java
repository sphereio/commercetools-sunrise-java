package com.commercetools.sunrise.common.sessions;

import com.google.inject.ImplementedBy;

import java.util.Optional;

/**
 * Represents a strategy used to keep information in session.
 */
@ImplementedBy(PlayHttpSessionStrategy.class)
public interface HttpSessionStrategy {

    /**
     * Finds the value associated with the given key in session.
     * @param key the session key
     * @return the value found in session, or empty if not found
     */
    Optional<String> findValueByKey(final String key);

    /**
     * Saves the value in session
     * @param key
     * @param value
     */
    void overwriteValueByKey(final String key, final String value);

    void removeValueByKey(final String key);
}
