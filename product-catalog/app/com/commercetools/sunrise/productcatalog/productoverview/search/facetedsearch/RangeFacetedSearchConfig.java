package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import io.sphere.sdk.facets.RangeFacetBuilder;
import io.sphere.sdk.products.ProductProjection;

public class RangeFacetedSearchConfig extends FacetedSearchConfig<RangeFacetBuilder<ProductProjection>> {

    private final RangeFacetBuilder<ProductProjection> rangeFacetBuilder;

    private RangeFacetedSearchConfig(final RangeFacetBuilder<ProductProjection> facetBuilder, final double position) {
        super(facetBuilder, position);
        this.rangeFacetBuilder = facetBuilder;
    }

    @Override
    public RangeFacetBuilder<ProductProjection> getFacetBuilder() {
        return rangeFacetBuilder;
    }

    public static RangeFacetedSearchConfig of(final RangeFacetBuilder<ProductProjection> rangeFacetBuilder, final double position) {
        return new RangeFacetedSearchConfig(rangeFacetBuilder, position);
    }
}