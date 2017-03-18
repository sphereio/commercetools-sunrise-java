package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetMapperSettings;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;

public interface SimpleCategoryTreeFacetedSearchFormSettings extends SimpleTermFacetedSearchFormSettings<ProductProjection> {

    @Override
    default boolean isMultiSelect() {
        return false;
    }

    @Override
    default boolean isMatchingAll() {
        return false;
    }

    @Nullable
    @Override
    default Long getThreshold() {
        return null;
    }

    @Nullable
    @Override
    default Long getLimit() {
        return null;
    }

    @Nullable
    @Override
    default TermFacetMapperSettings getMapperSettings() {
        return TermFacetMapperSettings.of("categoryTree", null);
    }

    static SimpleCategoryTreeFacetedSearchFormSettings of(final String fieldName, final String fieldLabel, final String attributePath,
                                                          final boolean countDisplayed, @Nullable final String uiType) {
        return new SimpleCategoryTreeFacetedSearchFormSettingsImpl(fieldName, fieldLabel, attributePath, countDisplayed, uiType);
    }
}
