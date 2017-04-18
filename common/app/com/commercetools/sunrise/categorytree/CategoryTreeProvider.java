package com.commercetools.sunrise.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.search.TermFacetExpression;
import io.sphere.sdk.search.TermFacetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.queries.QueryExecutionUtils.queryAll;
import static java.util.stream.Collectors.toList;

public final class CategoryTreeProvider implements Provider<CategoryTree> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryTreeProvider.class);

    private final CategoryTreeConfiguration configuration;
    private final SphereClient sphereClient;

    @Inject
    CategoryTreeProvider(final CategoryTreeConfiguration configuration, final SphereClient sphereClient) {
        this.configuration = configuration;
        this.sphereClient = sphereClient;
    }

    @Override
    public CategoryTree get() {
        final CategoryTree categoryTree = blockingWait(createCategoryTree(), Duration.ofSeconds(30));
        LOGGER.info("Fetched category tree with " + categoryTree.getAllAsFlatList().size() + " categories");
        return categoryTree;
    }

    private CompletionStage<CategoryTree> createCategoryTree() {
        final CompletionStage<CategoryTree> categoryTreeStage = fetchCategoryTree();
        if (configuration.discardEmpty()) {
            return categoryTreeStage.thenCombine(fetchCategoryFacetResult(),
                    (categoryTree, facetResult) -> filteredCategoryTree(facetResult, categoryTree));
        }
        return categoryTreeStage;
    }

    private CategoryTree filteredCategoryTree(@Nullable final TermFacetResult facetResult, final CategoryTree categoryTree) {
        if (facetResult == null) {
            LOGGER.error("Could not discard categories due to missing facet in the result");
        } else if (facetResult.getOther() > 0) {
            LOGGER.warn("Could not discard categories as some were not represented in the facet result due to the existing terms limit");
        } else {
            final List<Category> categories = categoryTree.getAllAsFlatList().stream()
                    .filter(category -> !isLeafWithNoProducts(category, facetResult, categoryTree))
                    .collect(toList());
            return CategoryTree.of(categories);
        }
        return categoryTree;
    }

    private boolean isLeafWithNoProducts(final Category category, final TermFacetResult facetResult, final CategoryTree baseCategoryTree) {
        return baseCategoryTree.findChildren(category).isEmpty()
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

    private CompletionStage<CategoryTree> fetchCategoryTree() {
        final List<QuerySort<Category>> sortExpressions = configuration.sortExpressions().stream()
                .map(QuerySort::<Category>of)
                .collect(toList());
        final CategoryQuery query = CategoryQuery.of()
                .withSort(sortExpressions);
        return queryAll(sphereClient, query)
                .thenApply(CategoryTree::of);
    }
}
