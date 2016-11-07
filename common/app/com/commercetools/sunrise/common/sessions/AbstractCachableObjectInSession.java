package com.commercetools.sunrise.common.sessions;

import play.Configuration;
import play.cache.CacheApi;
import play.mvc.Http;

import java.util.Optional;
import java.util.UUID;

/**
 * Enables to manage the data kept in session of a particular object.
 * For example, the session manager of the cart might save the ID, an instance of the mini cart and other related
 * information in session, taking care of keeping it up to date and remove it when it's needed.
 * @param <T> Class of the object represented in session
 */
public abstract class AbstractCachableObjectInSession<T> extends AbstractObjectInSession<T> {

    private static final String DEFAULT_SESSION_ID_KEY = "sunrise-session-id";
    private final String sessionIdKey;
    private final CacheApi cacheApi;

    protected AbstractCachableObjectInSession(final Http.Session session, final Configuration configuration,
                                              final CacheApi cacheApi) {
        super(session);
        this.cacheApi = cacheApi;
        this.sessionIdKey = configuration.getString("session.id", DEFAULT_SESSION_ID_KEY);
    }

    /**
     * Finds the object in cache for the given key.
     * @param sessionKey the session key
     * @return the object in cache for that session key, or empty if it could not be found (either in session or cache)
     */
    @Override
    protected final <U> Optional<U> findObjectByKey(final String sessionKey, final Class<U> clazz) {
        return findValueByKey(sessionKey)
                .flatMap(cachedKey -> {
                    final Optional<U> value = Optional.ofNullable(cacheApi.get(cachedKey));
                    if (value.isPresent()) {
                        logger.debug("Loaded from cache \"{}\" = {}", cachedKey, value.get());
                    } else {
                        logger.debug("Not found in cache \"{}\"", cachedKey);
                    }
                    return value;
                });
    }

    /**
     * Overwrites the key in the session/cache with the given value.
     * @param sessionKey the session key
     * @param value the value to be set in session
     */
    @Override
    protected final <U> void overwriteObjectByKey(final String sessionKey, final U value) {
        final String cacheKey = getCacheKey(sessionKey);
        cacheApi.set(cacheKey, value);
        logger.debug("Saved in cache \"{}\" = {}", cacheKey, value);
        overwriteValueByKey(sessionKey, cacheKey);
    }

    /**
     * Removes the key from the session/cache.
     * @param sessionKey the session key to be removed from session
     */
    @Override
    protected final void removeObjectByKey(final String sessionKey) {
        findValueByKey(sessionKey)
                .ifPresent(cacheKey -> {
                    cacheApi.remove(cacheKey);
                    logger.debug("Removed from cache \"{}\"", cacheKey);
                    removeValueByKey(sessionKey);
                });
    }

    /**
     * Obtains the cache key for that session key or generates a unique key for that session if not present.
     * @param sessionKey the session key
     * @return the cache key associated with that session and session key
     */
    private String getCacheKey(final String sessionKey) {
        return findValueByKey(sessionKey)
                .orElseGet(() -> getSessionId() + sessionKey);
    }

    /**
     * Obtains the session ID or generates a new one if not present.
     * @return the ID associated with that session
     */
    private String getSessionId() {
        return findValueByKey(sessionIdKey)
                .orElseGet(() -> {
                    final String uuid = UUID.randomUUID().toString();
                    overwriteValueByKey(sessionIdKey, uuid);
                    return uuid;
                });
    }
}
