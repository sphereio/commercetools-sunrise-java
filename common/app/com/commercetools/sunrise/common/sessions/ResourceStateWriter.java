package com.commercetools.sunrise.common.sessions;

import javax.annotation.Nullable;

/**
 * Enables a particular object to be saved somewhere.
 * The interface does not define how this object is represented (the entire object, only specific parts...).
 * @param <T> Class of the object represented
 */
public interface ResourceStateWriter<T> {

    /**
     * Saves the object, replacing it if a previous version already existed.
     * If the value is {@code null}, it removes the object instead.
     * @param value the instance of the object used to update the saved data, or {@code null} to remove all data
     */
    void overwrite(@Nullable final T value);

    /**
     * Removes the object from the state.
     */
    void remove();
}
