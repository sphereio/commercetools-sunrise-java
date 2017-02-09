package com.commercetools.sunrise.common.categorytree;

import com.google.inject.Provider;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public final class CategoryTreeInNewProvider implements Provider<CategoryTree> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryTreeInNewProvider.class);
    private static final String CONFIG_CATEGORY_NEW_EXT_ID = "common.newCategoryExternalId";

    @Inject
    private CategoryTree categoryTree;
    @Inject
    private CategoryTreeRefresher categoryTreeRefresher;
    @Inject
    private Configuration configuration;

    @Override
    public CategoryTree get() {
        return RefreshableCategorySubtree.of(getBuildStrategy(), categoryTreeRefresher);
    }

    private Supplier<CategoryTree> getBuildStrategy() {
        return () -> {
            final List<Category> categories = getCategoryNew(categoryTree)
                    .map(Collections::singletonList)
                    .orElseGet(Collections::emptyList);
            final CategoryTree categoryTreeInNew = categoryTree.getSubtree(categories);
            logger.info("Provide CategoryTreeInNew with " + categoryTreeInNew.getAllAsFlatList().size() + " categories");
            return categoryTreeInNew;
        };
    }

    private Optional<Category> getCategoryNew(CategoryTree mainTree) {
        return Optional.ofNullable(configuration.getString(CONFIG_CATEGORY_NEW_EXT_ID))
                .flatMap(mainTree::findByExternalId);
    }
}
