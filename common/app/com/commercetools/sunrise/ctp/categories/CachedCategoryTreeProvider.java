package com.commercetools.sunrise.ctp.categories;

import io.sphere.sdk.categories.CategoryTree;
import play.cache.CacheApi;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public final class CachedCategoryTreeProvider implements Provider<CategoryTree> {

    private final CacheApi cacheApi;
    private final CategoriesSettings configuration;
    private final CategoryTreeProvider categoryTreeProvider;

    @Inject
    CachedCategoryTreeProvider(final CacheApi cacheApi, final CategoriesSettings configuration,
                               final CategoryTreeProvider categoryTreeProvider) {
        this.cacheApi = cacheApi;
        this.configuration = configuration;
        this.categoryTreeProvider = categoryTreeProvider;
    }

    @Override
    public CategoryTree get() {
        return configuration.cacheExpiration()
                .map(expiration -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeProvider::get, expiration))
                .orElseGet(() -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeProvider::get));
    }
}