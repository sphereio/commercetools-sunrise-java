package com.commercetools.sunrise.common.sessions;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Enables a particular object to be kept in session.
 * It is not defined how this object is represented in session (the entire object, only specific parts...).
 */
@RequestScoped
public class PlaySession implements SimpleSession {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Http.Session session;

    @Inject
    public PlaySession(final Http.Session session) {
        this.session = session;
    }

    /**
     * Finds the value in session for the given key.
     * @param sessionKey the session key
     * @return the value for that session key, or empty if it could not be found
     */
    @Override
    public Optional<String> findValueByKey(final String sessionKey) {
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
    @Override
    public void overwriteValueByKey(final String sessionKey, final String value) {
        session.put(sessionKey, value);
        logger.debug("Saved in session \"{}\" = {}", sessionKey, value);
    }

    /**
     * Removes the key from the session.
     * @param sessionKey the session key to be removed from session
     */
    @Override
    public void removeValueByKey(final String sessionKey) {
        final String oldValue = session.remove(sessionKey);
        logger.debug("Removed from session \"{}\" = {}", sessionKey, oldValue);
    }
}
