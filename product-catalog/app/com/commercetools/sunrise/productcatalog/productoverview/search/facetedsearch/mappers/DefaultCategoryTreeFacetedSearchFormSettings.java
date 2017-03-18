package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.mappers;

import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;

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

    @Override
    public Optional<Category> findSelectedCategory(final String categoryIdentifier) {
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
