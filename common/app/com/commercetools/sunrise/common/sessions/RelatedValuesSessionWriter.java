package com.commercetools.sunrise.common.sessions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Class to use as a base to all session managers, with all common logic.
 * For example, the session manager of the cart might save the ID, an instance of the mini cart and other related
 * information in session, taking care of keeping it up to date and remove it when it's needed.
 * @param <T> Class of the object in session
 */
public abstract class RelatedValuesSessionWriter<T> implements SessionWriter<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Replaces all the information related to the object in session with the given value.
     * If the value is {@code null}, it removes all data from session instead.
     * @param value the instance of the object used to update the session, or {@code null} to remove all data
     */
    @Override
    public void overwrite(@Nullable final T value) {
        if (value != null) {
            overwriteRelatedValuesInSession(value);
        } else {
            removeRelatedValuesFromSession();
        }
    }

    /**
     * Removes all the information related to this class.
     */
    @Override
    public void remove() {
        removeRelatedValuesFromSession();
    }

    /**
     * Takes care of overwriting all related values to this object.
     * For example, in the case of the cart, those might be overwriting the cart ID value, the mini cart instance, etc.
     * @param value the instance of the object used to update the session
     */
    protected abstract void overwriteRelatedValuesInSession(@NotNull final T value);

    /**
     * Takes care of overwriting all related values to this object.
     * For example, in the case of the cart, those might be removing the cart ID value, the mini cart instance, etc.
     */
    protected abstract void removeRelatedValuesFromSession();
}