package com.commercetools.sunrise.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.queries.QuerySort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.queries.QueryExecutionUtils.queryAll;
import static java.util.stream.Collectors.toList;

@Singleton
public final class CategoryTreeProvider implements Provider<CategoryTree> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryTreeProvider.class);
    private static final int CATEGORIES_LIMIT = 500;

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
            return categoryTreeStage
                    .thenCombine(fetchEmptyCategoriesId(), this::discardEmptyCategories)
                    .thenApply(CategoryTree::of);
        }
        return categoryTreeStage;
    }

    List<Category> discardEmptyCategories(final CategoryTree categoryTree, final List<String> emptyCategoriesId) {
        return categoryTree.getAllAsFlatList().stream()
                        .filter(category -> hasProducts(category, emptyCategoriesId))
                        .collect(toList());
    }

    private boolean hasProducts(final Category category, final List<String> emptyCategoriesId) {
        return emptyCategoriesId.stream()
                .noneMatch(emptyCategory -> emptyCategory.equals(category.getId()));
    }

    private CompletionStage<List<String>> fetchEmptyCategoriesId() {
        final CategoriesWithProductCountQuery query = new CategoriesWithProductCountQuery(CATEGORIES_LIMIT);
        return sphereClient.execute(query)
                .thenApply(result -> {
                    if (result.getTotal() > CATEGORIES_LIMIT) {
                        LOGGER.warn("Some empty categories might not be discarded due to the current limit of " + CATEGORIES_LIMIT);
                    }
                    return result.getResults().stream()
                            .filter(category -> !category.hasProducts())
                            .map(CategoryWithProductCount::getId)
                            .collect(toList());
                });
    }

    private CompletionStage<CategoryTree> fetchCategoryTree() {
        return queryAll(sphereClient, buildQuery())
                .thenApply(CategoryTree::of);
    }

    CategoryQuery buildQuery() {
        final List<QuerySort<Category>> sortExpressions = configuration.sortExpressions().stream()
                .map(QuerySort::<Category>of)
                .collect(toList());
        return CategoryQuery.of()
                .withSort(sortExpressions);
    }
}
