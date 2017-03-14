package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import io.sphere.sdk.facets.FacetType;
import io.sphere.sdk.facets.RangeFacetBuilder;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.FacetRange;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ConfigurableRangeFacetedSearchFormSettings<T> extends ConfigurableFacetedSearchFormSettings<T> implements RangeFacetedSearchFormSettings<T> {

    private static final String INITIAL_RANGES = "initialRanges";

    protected ConfigurableRangeFacetedSearchFormSettings(final Configuration configuration, final int position,
                                                         final List<? extends FacetUIType> facetUITypes,
                                                         final List<? extends FacetMapperType> facetMapperTypes) {
        super(configuration, position, facetUITypes, facetMapperTypes);
    }

    private static List<FacetRange<String>> convertInitialRangesToFacetRanges(String initialRanges) {
        List<FacetRange<String>> result = new ArrayList<>();
        for (String initialRange : initialRanges.split(",")) {
            String[] tempArr = initialRange.split("-");
            if (tempArr[0].equals("*")) {
                result.add(FacetRange.lessThan(tempArr[1]));
            } else if (tempArr[1].equals("*")) {
                result.add(FacetRange.atLeast(tempArr[0]));
            } else {
                result.add(FacetRange.of(tempArr[0], tempArr[1]));
            }
        }
        return result;
    }

    private static RangeFacetBuilder<ProductProjection> getRangeFacetBuilder(final FacetType type, final Configuration facetConfig) {
        List<FacetRange<String>> initialFacetRanges = new ArrayList<>();
        if (type == SunriseFacetUIType.BUCKET_RANGE) {
            String initialRanges = Optional.ofNullable(facetConfig.getString(INITIAL_RANGES))
                    .orElseThrow(() -> new SunriseConfigurationException("Missing initial ranges", INITIAL_RANGES, facetConfig));
            initialFacetRanges.addAll(convertInitialRangesToFacetRanges(initialRanges));
        } else {
            initialFacetRanges.add(FacetRange.of(null, null));
        }
    }
}
