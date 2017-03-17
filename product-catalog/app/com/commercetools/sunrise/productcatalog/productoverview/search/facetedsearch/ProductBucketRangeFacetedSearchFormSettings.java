package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.bucketranges.ConfigurableBucketRangeFacetedSearchFormOption;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.ConfigurableBucketRangeFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

public final class ProductBucketRangeFacetedSearchFormSettings extends ConfigurableBucketRangeFacetedSearchFormSettings<ProductProjection> {

    public ProductBucketRangeFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration, ConfigurableBucketRangeFacetedSearchFormOption::new);
    }
}
