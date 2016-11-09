package com.commercetools.sunrise.common.sessions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Allows to keep in session only some selected parts of information derived from the object, in opposite to the whole object.
 * This might come in handy when the object contains sensible or unused data, it is too large or different data
 * requires different session storing strategies (e.g. save the cart ID in session and the mini cart in the session cache).
 * @param <T> Class of the object represented in session
 */
public abstract class AssociatedDataResourceStateWriter<T> implements ResourceStateWriter<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void overwrite(@Nullable final T value) {
        if (value != null) {
            overwriteAssociatedDataInSession(value);
        } else {
            removeAssociatedDataFromSession();
        }
    }

    @Override
    public void remove() {
        removeAssociatedDataFromSession();
    }

    /**
     * Saves the object's associated information in session, replacing them if a previous version already existed.
     * @param value the instance of the object used to update the session
     */
    protected abstract void overwriteAssociatedDataInSession(@NotNull final T value);

    /**
     * Removes the object's associated information from the session.
     */
    protected abstract void removeAssociatedDataFromSession();
}