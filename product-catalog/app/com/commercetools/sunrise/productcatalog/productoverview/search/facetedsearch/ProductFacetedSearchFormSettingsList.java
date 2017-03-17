package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.ConfigurableFacetedSearchFormSettingsList;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductFacetedSearchFormSettingsList extends ConfigurableFacetedSearchFormSettingsList<ProductProjection> {

    private static final String CONFIG = "pop.facets";

    @Inject
    public ProductFacetedSearchFormSettingsList(final Configuration configuration) {
        super(configuration(configuration),
                ProductTermFacetedSearchFormSettings::new,
                ProductSliderRangeFacetedSearchFormSettings::new,
                ProductBucketRangeFacetedSearchFormSettings::new);
    }

    private static List<Configuration> configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfigList(CONFIG)).orElseGet(Collections::emptyList);
    }
}
