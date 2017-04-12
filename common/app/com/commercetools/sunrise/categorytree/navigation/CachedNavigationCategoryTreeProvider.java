package com.commercetools.sunrise.categorytree.navigation;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.TermFacetExpression;
import io.sphere.sdk.search.TermFacetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.cache.CacheApi;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

public final class CachedNavigationCategoryTreeProvider implements Provider<CategoryTree> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedNavigationCategoryTreeProvider.class);

    private final NavigationCategoryTreeConfiguration configuration;
    private final CacheApi cacheApi;
    private final SphereClient sphereClient;
    private final CategoryTree categoryTree;

    @Inject
    CachedNavigationCategoryTreeProvider(final NavigationCategoryTreeConfiguration configuration, final CacheApi cacheApi,
                                         final SphereClient sphereClient, final CategoryTree categoryTree) {
        this.configuration = configuration;
        this.cacheApi = cacheApi;
        this.sphereClient = sphereClient;
        this.categoryTree = categoryTree;
    }

    @Override
    public CategoryTree get() {
        return configuration.cacheExpiration()
                .map(expiration -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeSupplier(), expiration))
                .orElseGet(() -> cacheApi.getOrElse(configuration.cacheKey(), categoryTreeSupplier()));
    }

    private Callable<CategoryTree> categoryTreeSupplier() {
        return () -> {
            final CategoryTree navCategoryTree = blockingWait(fetchCategoryTree(), Duration.ofSeconds(30));
            LOGGER.info("Caching navigation category tree with " + navCategoryTree.getAllAsFlatList().size() + " categories");
            return navCategoryTree;
        };
    }

    private CompletionStage<CategoryTree> fetchCategoryTree() {
        if (configuration.discardEmpty()) {
            return fetchCategoryFacetResult()
                    .thenApply(this::filteredCategoryTree)
                    .thenApply(this::findNavigationCategoryTree);
        }
        return completedFuture(findNavigationCategoryTree(categoryTree));
    }

    private CategoryTree findNavigationCategoryTree(final CategoryTree categoryTree) {
        return configuration.rootExternalId()
                .flatMap(categoryTree::findByExternalId)
                .map(categoryTree::findChildren)
                .map(categoryTree::getSubtree)
                .orElse(categoryTree);
    }

    private CategoryTree filteredCategoryTree(@Nullable final TermFacetResult facetResult) {
        if (facetResult == null) {
            LOGGER.error("Could not discard categories due to missing facet in the result");
        } else if (facetResult.getOther() > 0) {
            LOGGER.warn("Could not discard categories as some were not represented in the facet result due to the existing limit");
        } else {
            final List<Category> categories = categoryTree.getAllAsFlatList().stream()
                    .filter(category -> !isLeafWithNoProducts(category, facetResult))
                    .collect(toList());
            return CategoryTree.of(categories);
        }
        return categoryTree;
    }

    private boolean isLeafWithNoProducts(final Category category, final TermFacetResult facetResult) {
        return categoryTree.findChildren(category).isEmpty()
                && facetResult.getTerms().stream()
                    .noneMatch(termStats -> termStats.getTerm().equals(category.getId()));
    }

    private CompletionStage<TermFacetResult> fetchCategoryFacetResult() {
        final TermFacetExpression<ProductProjection> facetExpression = ProductProjectionSearchModel.of().facet().categories().id().allTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofCurrent()
                .withFacets(facetExpression)
                .withLimit(0);
        return sphereClient.execute(search)
                .thenApply(result -> result.getFacetResult(facetExpression));
    }
}
