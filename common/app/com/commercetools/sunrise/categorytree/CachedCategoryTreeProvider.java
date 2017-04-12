package com.commercetools.sunrise.categorytree;

import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.queries.QuerySort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.cache.CacheApi;

import javax.inject.Inject;
import javax.inject.Provider;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.queries.QueryExecutionUtils.queryAll;

public final class CachedCategoryTreeProvider implements Provider<CategoryTree> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedCategoryTreeProvider.class);

    private final CategoryTreeConfiguration configuration;
    private final CacheApi cacheApi;
    private final SphereClient sphereClient;

    @Inject
    CachedCategoryTreeProvider(final CategoryTreeConfiguration configuration, final CacheApi cacheApi,
                               final SphereClient sphereClient) {
        this.configuration = configuration;
        this.cacheApi = cacheApi;
        this.sphereClient = sphereClient;
    }

    @Override
    public CategoryTree get() {
        return configuration.cacheExpiration()
                .map(expiration -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeSupplier(), expiration))
                .orElseGet(() -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeSupplier()));
    }

    private Callable<CategoryTree> categoryTreeSupplier() {
        return () -> {
            final CategoryTree categoryTree = blockingWait(fetchCategories(), Duration.ofSeconds(30));
            LOGGER.info("Caching category tree with " + categoryTree.getAllAsFlatList().size() + " categories");
            return categoryTree;
        };
    }

    private CompletionStage<CategoryTree> fetchCategories() {
        final CategoryQuery query = CategoryQuery.of()
                .plusSort(QuerySort.of("orderHint asc"));
        return queryAll(sphereClient, query)
                .thenApply(CategoryTree::of);
    }
}
