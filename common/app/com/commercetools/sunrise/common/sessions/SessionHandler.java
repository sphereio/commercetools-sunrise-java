package com.commercetools.sunrise.common.sessions;

import play.mvc.Http;

import javax.annotation.Nullable;

/**
 * Enables to manage the data kept in session of a particular object.
 * For example, the session manager of the cart might save the ID, an instance of the mini cart and other related
 * information in session, taking care of keeping it up to date and remove it when it's needed.
 * @param <T> Class of the object in session
 */
public interface SessionHandler<T> {

    /**
     * Replaces all the information related to the object in session with the given value.
     * If the value is {@code null}, it removes all data from session instead.
     * @param session the HTTP session to be updated
     * @param value the instance of the object used to update the session, or {@code null} to remove all data
     */
    void overwriteInSession(final Http.Session session, @Nullable final T value);

    /**
     * Removes all the information related to this class.
     * @param session the HTTP session to be updated
     */
    void removeFromSession(final Http.Session session);
}
