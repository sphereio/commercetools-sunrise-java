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
public class PlayHttpSessionStrategy implements HttpSessionStrategy {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Http.Session session;

    @Inject
    public PlayHttpSessionStrategy(final Http.Session session) {
        this.session = session;
    }

    /**
     * Finds the value in session for the given key.
     * @param key the session key
     * @return the value for that session key, or empty if it could not be found
     */
    @Override
    public Optional<String> findValueByKey(final String key) {
        final Optional<String> value = Optional.ofNullable(session.get(key));
        if (value.isPresent()) {
            logger.debug("Loaded from session \"{}\" = {}", key, value.get());
        } else {
            logger.debug("Not found in session \"{}\"", key);
        }
        return value;
    }

    /**
     * Overwrites the key in the session with the given value.
     * @param key the session key
     * @param value the value to be set in session
     */
    @Override
    public void overwriteValueByKey(final String key, final String value) {
        session.put(key, value);
        logger.debug("Saved in session \"{}\" = {}", key, value);
    }

    /**
     * Removes the key from the session.
     * @param key the session key to be removed from session
     */
    @Override
    public void removeValueByKey(final String key) {
        final String oldValue = session.remove(key);
        logger.debug("Removed from session \"{}\" = {}", key, oldValue);
    }
}
