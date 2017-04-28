package com.commercetools.sunrise.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.client.SphereClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

class CategoryTreeFilterImpl implements CategoryTreeFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryTreeFilter.class);
    private static final long CATEGORIES_LIMIT = 500;

    private final CategoryTreeConfiguration configuration;
    private final SphereClient sphereClient;

    @Inject
    CategoryTreeFilterImpl(final CategoryTreeConfiguration configuration, final SphereClient sphereClient) {
        this.configuration = configuration;
        this.sphereClient = sphereClient;
    }

    @Override
    public CompletionStage<CategoryTree> filter(final CategoryTree categoryTree) {
        if (configuration.discardEmpty()) {
            return fetchEmptyCategoryIds()
                    .thenApply(emptyCategoryIds -> discardEmptyCategories(categoryTree, emptyCategoryIds))
                    .thenApply(CategoryTree::of);
        }
        return completedFuture(categoryTree);
    }

    private CompletionStage<List<String>> fetchEmptyCategoryIds() {
        final CategoriesWithProductCountQuery query = new CategoriesWithProductCountQuery(CATEGORIES_LIMIT, 0L);
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

    private List<Category> discardEmptyCategories(final CategoryTree categoryTree, final List<String> emptyCategoryIds) {
        return categoryTree.getAllAsFlatList().stream()
                .filter(category -> hasProducts(category, emptyCategoryIds))
                .collect(toList());
    }

    private boolean hasProducts(final Category category, final List<String> emptyCategoryIds) {
        return emptyCategoryIds.stream()
                .noneMatch(emptyCategoryId -> emptyCategoryId.equals(category.getId()));
    }
}
