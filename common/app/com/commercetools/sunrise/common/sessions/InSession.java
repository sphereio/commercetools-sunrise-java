package com.commercetools.sunrise.common.sessions;

import javax.annotation.Nullable;

/**
 * Enables an object to be kept in session of a particular object.
 * For example, the session manager of the cart might save the ID, an instance of the mini cart and other related
 * information in session, taking care of keeping it up to date and remove it when it's needed.
 * @param <T> Class of the object in session
 */
public interface InSession<T> {

    /**
     * Replaces all the information related to the object in session with the given value.
     * If the value is {@code null}, it removes all data from session instead.
     * @param value the instance of the object used to update the session, or {@code null} to remove all data
     */
    void overwrite(@Nullable final T value);

    /**
     * Removes all the information related to this class.
     */
    void remove();
}
