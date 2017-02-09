package com.commercetools.sunrise.common.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.queries.QueryExecutionUtils.queryAll;
import static java.util.stream.Collectors.toList;

/**
 * A category tree with capabilities of refreshing contained tree and notifying subscribers when such action occurs.
 */
public final class RefreshableCategoryTree extends CategoryTreeWrapper implements CategoryTreeRefresher {

    private static final Logger logger = LoggerFactory.getLogger(RefreshableCategoryTree.class);
    private final SphereClient sphereClient;
    private List<CategoryTreeRefreshListener> categorySubtrees = new ArrayList<>();

    private RefreshableCategoryTree(final SphereClient sphereClient) {
        this.sphereClient = sphereClient;
        refresh();
    }

    public synchronized void refresh() {
        setCategoryTree(fetchFreshCategoryTree(sphereClient));
        categorySubtrees.forEach(CategoryTreeRefreshListener::onRefresh);
    }

    public static RefreshableCategoryTree of(final SphereClient sphereClient) {
        return new RefreshableCategoryTree(sphereClient);
    }

    private static CategoryTree fetchFreshCategoryTree(final SphereClient client) {
        final List<Category> categories = fetchCategories(client);
        logger.debug("Provide RefreshableCategoryTree with " + categories.size() + " categories");
        return CategoryTree.of(categories);
    }

    private static List<Category> fetchCategories(final SphereClient client) {
        final List<Category> categories = blockingWait(queryAll(client, CategoryQuery.of()), 30, TimeUnit.SECONDS);
        return sortCategories(categories);
    }

    private static List<Category> sortCategories(final List<Category> categories) {
        return categories.stream()
                .sorted(new ByOrderHintCategoryComparator())
                .collect(toList());
    }

    @Override
    public void addListener(CategoryTreeRefreshListener refreshListener) {
        categorySubtrees.add(refreshListener);
    }

}
