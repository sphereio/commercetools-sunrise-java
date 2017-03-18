package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.terms.ConfigurableTermFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

public final class ProductTermFacetedSearchFormSettings extends ConfigurableTermFacetedSearchFormSettings<ProductProjection> {

    public ProductTermFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
    }
}
