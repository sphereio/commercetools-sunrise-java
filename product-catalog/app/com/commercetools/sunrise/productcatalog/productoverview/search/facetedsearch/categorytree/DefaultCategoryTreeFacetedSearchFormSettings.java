package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class DefaultCategoryTreeFacetedSearchFormSettings extends AbstractFacetedSearchFormSettingsWithOptions<ProductProjection, SimpleCategoryTreeFacetedSearchFormSettings> implements CategoryTreeFacetedSearchFormSettings {

    private final CategoryFinder categoryFinder;

    public DefaultCategoryTreeFacetedSearchFormSettings(final SimpleCategoryTreeFacetedSearchFormSettings settings,
                                                        final Locale locale, final CategoryFinder categoryFinder) {
        super(settings, locale);
        this.categoryFinder = categoryFinder;
    }

    @Override
    public String getFieldName() {
        return getSettings().getFieldName();
    }

    @Nullable
    @Override
    public String mapFieldValueToValue(final String fieldValue) {
        return findCategory(fieldValue)
                .map(Resource::getId)
                .orElse(null);
    }

    private Optional<Category> findCategory(final String categoryIdentifier) {
        if (!categoryIdentifier.isEmpty()) {
            try {
                return categoryFinder.apply(categoryIdentifier)
                        .toCompletableFuture().get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                // Returns default empty
            }
        }
        return Optional.empty();
    }
}
