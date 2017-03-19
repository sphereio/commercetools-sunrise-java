package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

public final class ConfigurableCategoryTreeFacetedSearchFormSettings extends AbstractConfigurableFacetedSearchFormSettings implements SimpleCategoryTreeFacetedSearchFormSettings {

    private final String fieldName;

    public ConfigurableCategoryTreeFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
        this.fieldName = fieldName(configuration);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
