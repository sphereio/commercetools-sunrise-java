package com.commercetools.sunrise.categorytree;

import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class CategoryTreeConfiguration {

    private static final String CONFIG_CACHE_KEY = "categoryTree.cache.key";
    private static final String DEFAULT_CACHE_KEY = "categoryTree";
    private static final String CONFIG_CACHE_EXPIRATION = "categoryTree.cache.expiration";

    private final Integer cacheExpiration;
    private final String cacheKey;

    @Inject
    CategoryTreeConfiguration(final Configuration configuration) {
        this.cacheKey = configuration.getString(CONFIG_CACHE_KEY, DEFAULT_CACHE_KEY);
        this.cacheExpiration = configuration.getInt(CONFIG_CACHE_EXPIRATION);
    }

    public String cacheKey() {
        return cacheKey;
    }

    public Optional<Integer> cacheExpiration() {
        return Optional.ofNullable(cacheExpiration);
    }
}
