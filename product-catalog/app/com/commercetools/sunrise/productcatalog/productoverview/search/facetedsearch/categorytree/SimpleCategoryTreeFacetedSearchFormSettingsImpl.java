package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.search.facetedsearch.AbstractSimpleFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;
import javax.inject.Inject;

final class SimpleCategoryTreeFacetedSearchFormSettingsImpl extends AbstractSimpleFacetedSearchFormSettings<ProductProjection> implements SimpleCategoryTreeFacetedSearchFormSettings {

    private final String fieldName;

    @Inject
    public SimpleCategoryTreeFacetedSearchFormSettingsImpl(final String fieldName, final String fieldLabel, final String attributePath,
                                                           final boolean countDisplayed, @Nullable final String uiType) {
        super(fieldLabel, attributePath, countDisplayed, uiType);
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
