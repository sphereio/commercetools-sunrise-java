package com.commercetools.sunrise.common.sessions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Class to use as a base to all session managers, with all common logic.
 * @param <T> Class of the object in session
 */
public abstract class AbstractInSession<T> implements InSession<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Http.Session session;

    protected AbstractInSession(final Http.Session session) {
        this.session = session;
    }

    @Override
    public void overwrite(@Nullable final T value) {
        if (value != null) {
            overwriteRelatedValuesInSession(value);
        } else {
            removeRelatedValuesFromSession();
        }
    }

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

    /**
     * Finds the value in session for the given key.
     * @param sessionKey the session key
     * @return the value for that session key, or empty if it could not be found
     */
    protected final Optional<String> findValueByKey(final String sessionKey) {
        final Optional<String> value = Optional.ofNullable(session.get(sessionKey));
        if (value.isPresent()) {
            logger.debug("Loaded from session \"{}\" = {}", sessionKey, value.get());
        } else {
            logger.debug("Not found in session \"{}\"", sessionKey);
        }
        return value;
    }

    /**
     * Overwrites the key in the session with the given value.
     * @param sessionKey the session key
     * @param value the value to be set in session
     */
    protected final void overwriteValueByKey(final String sessionKey, final String value) {
        session.put(sessionKey, value);
        logger.debug("Saved in session \"{}\" = {}", sessionKey, value);
    }

    /**
     * Removes the key from the session.
     * @param sessionKey the session key to be removed from session
     */
    protected final void removeValueByKey(final String sessionKey) {
        final String oldValue = session.remove(sessionKey);
        logger.debug("Removed from session \"{}\" = {}", sessionKey, oldValue);
    }

    /**
     * Removes a list of keys from the session.
     * @param sessionKeys the list of session keys to be removed from session
     * @deprecated use {@link #removeValueByKey(String)} instead
     */
    @Deprecated
    protected final void removeValueByKey(final List<String> sessionKeys) {
        sessionKeys.forEach(this::removeValueByKey);
    }
}
