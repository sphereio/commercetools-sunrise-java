package com.commercetools.sunrise.common.sessions;

import play.cache.CacheApi;
import play.mvc.Http;

import java.util.Optional;

public abstract class AbstractCachedSessionHandler<T> extends AbstractSessionHandler<T> {

    protected <U> void overwriteCachedValueByKey(final Http.Session session, final CacheApi cacheApi, final String cacheKey, final String sessionKey, final U value) {
        cacheApi.set(cacheKey, value);
        logger.debug("Saved in cache \"{}\" = {}", cacheKey, value);
        overwriteValueByKey(session, sessionKey, cacheKey);
    }

    protected <U> Optional<U> findCachedValueByKey(final Http.Session session, final CacheApi cacheApi, final String sessionKey) {
        return findValueByKey(session, sessionKey)
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

    protected void removeCachedValueByKey(final Http.Session session, final CacheApi cacheApi, final String sessionKey) {
        findValueByKey(session, sessionKey)
                .ifPresent(cacheKey -> {
                    cacheApi.remove(cacheKey);
                    logger.debug("Removed from cache \"{}\"", cacheKey);
                    removeValueByKey(session, sessionKey);
                });
    }
}
