package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.sliderranges.ConfigurableSliderRangeFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

public final class ProductSliderRangeFacetedSearchFormSettings extends ConfigurableSliderRangeFacetedSearchFormSettings<ProductProjection> {

    public ProductSliderRangeFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
    }
}
