package com.commercetools.sunrise.common.categorytree;

import io.sphere.sdk.categories.CategoryTree;

import java.util.function.Supplier;

/**
 * A category tree with capabilities of reacting to external refresh action and rebuilding contained tree.
 */
public class RefreshableCategorySubtree extends CategoryTreeWrapper implements CategoryTreeRefreshListener {

    private final Supplier<CategoryTree> buildStrategy;

    private RefreshableCategorySubtree(Supplier<CategoryTree> buildStrategy,
                                       CategoryTreeRefresher categoryTreeRefresher) {
        this.buildStrategy = buildStrategy;
        categoryTreeRefresher.addListener(this);

        onRefresh();
    }

    static CategoryTree of(Supplier<CategoryTree> subtreeBuildStrategy, CategoryTreeRefresher categoryTreeRefresher) {
        return new RefreshableCategorySubtree(subtreeBuildStrategy, categoryTreeRefresher);
    }

    @Override
    public void onRefresh() {
        setCategoryTree(buildStrategy.get());
    }
}
