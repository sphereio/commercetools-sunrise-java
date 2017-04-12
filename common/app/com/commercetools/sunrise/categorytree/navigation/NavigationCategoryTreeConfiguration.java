package com.commercetools.sunrise.categorytree.navigation;

import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class NavigationCategoryTreeConfiguration {

    private static final String CONFIG_CACHE_KEY = "categoryTree.navigation.cache.key";
    private static final String DEFAULT_CACHE_KEY = "navigationCategoryTree";
    private static final String CONFIG_CACHE_EXPIRATION = "categoryTree.navigation.cache.expiration";
    private static final String CONFIG_ROOT_EXTERNAL_ID = "categoryTree.navigation.rootExternalId";
    private static final String CONFIG_DISCARD_EMPTY = "categoryTree.navigation.discardEmpty";

    private final boolean discardEmpty;
    @Nullable
    private final String rootExtId;
    @Nullable
    private final Integer cacheExpiration;
    private final String cacheKey;

    @Inject
    NavigationCategoryTreeConfiguration(final Configuration configuration) {
        this.cacheKey = configuration.getString(CONFIG_CACHE_KEY, DEFAULT_CACHE_KEY);
        this.cacheExpiration = configuration.getInt(CONFIG_CACHE_EXPIRATION);
        this.discardEmpty = configuration.getBoolean(CONFIG_DISCARD_EMPTY, false);
        this.rootExtId = configuration.getString(CONFIG_ROOT_EXTERNAL_ID);
    }

    public String cacheKey() {
        return cacheKey;
    }

    public Optional<Integer> cacheExpiration() {
        return Optional.ofNullable(cacheExpiration);
    }

    public boolean discardEmpty() {
        return discardEmpty;
    }

    public Optional<String> rootExternalId() {
        return Optional.ofNullable(rootExtId);
    }
}
