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
public abstract class AbstractSessionHandler<T> implements SessionHandler<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Takes care of overwriting all related values to this object.
     * For example, in the case of the cart, those might be overwriting the cart ID value, the mini cart instance, etc.
     * @param session the HTTP session to be updated
     * @param value the instance of the object used to update the session
     */
    protected abstract void overwriteRelatedValuesInSession(final Http.Session session, @NotNull final T value);

    /**
     * Takes care of overwriting all related values to this object.
     * For example, in the case of the cart, those might be removing the cart ID value, the mini cart instance, etc.
     * @param session the HTTP session to be updated
     */
    protected abstract void removeRelatedValuesFromSession(final Http.Session session);

    @Override
    public void overwriteInSession(final Http.Session session, @Nullable final T value) {
        if (value != null) {
            overwriteRelatedValuesInSession(session, value);
        } else {
            removeRelatedValuesFromSession(session);
        }
    }

    @Override
    public void removeFromSession(final Http.Session session) {
        removeRelatedValuesFromSession(session);
    }

    protected Optional<String> findValueByKey(final Http.Session session, final String sessionKey) {
        final Optional<String> value = Optional.ofNullable(session.get(sessionKey));
        if (value.isPresent()) {
            logger.debug("Loaded from session \"{}\" = {}", sessionKey, value.get());
        } else {
            logger.debug("Not found in session \"{}\"", sessionKey);
        }
        return value;
    }

    protected void overwriteValueByKey(final Http.Session session, final String sessionKey, final String value) {
        session.put(sessionKey, value);
        logger.debug("Saved in session \"{}\" = {}", sessionKey, value);
    }

    /**
     * Remove a list of keys from the session
     * @param session the HTTP session
     * @param sessionKeys a list of the keys to be removed from session
     * @deprecated use {@link #removeValueByKey(Http.Session, String)} instead
     */
    @Deprecated
    protected void removeValueByKey(final Http.Session session, final List<String> sessionKeys) {
        sessionKeys.forEach(key -> removeValueByKey(session, key));
    }

    protected void removeValueByKey(final Http.Session session, final String sessionKey) {
        final String oldValue = session.remove(sessionKey);
        logger.debug("Removed from session \"{}\" = {}", sessionKey, oldValue);
    }
}
