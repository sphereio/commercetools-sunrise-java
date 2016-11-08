package com.commercetools.sunrise.common.sessions;

import javax.annotation.Nullable;

/**
 * Enables a particular object to be kept in session.
 * It is not defined how this object is represented in session (the entire object, only specific parts...).
 * @param <T> Class of the object in session
 */
public interface SessionWriter<T> {

    /**
     * Saves the given value in session or replaces it if a previous version already existed.
     * If the value is {@code null}, it removes the object from session instead.
     * @param value the instance of the object used to update the session, or {@code null} to remove all data
     */
    void overwrite(@Nullable final T value);

    /**
     * Removes the object from the session.
     */
    void remove();
}
