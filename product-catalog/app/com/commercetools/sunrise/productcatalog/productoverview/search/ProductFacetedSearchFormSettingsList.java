package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.facetedsearch.ConfigurableFacetedSearchFormSettingsList;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Singleton
public final class ProductFacetedSearchFormSettingsList extends ConfigurableFacetedSearchFormSettingsList<ProductProjection> {

    private static final String CONFIG = "pop.facets";

    @Inject
    public ProductFacetedSearchFormSettingsList(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfigList(CONFIG)).orElseGet(Collections::emptyList), facetMapperTypes());
    }

    private static List<SunriseTermFacetMapperType> facetMapperTypes() {
        return asList(SunriseTermFacetMapperType.values());
    }
}
