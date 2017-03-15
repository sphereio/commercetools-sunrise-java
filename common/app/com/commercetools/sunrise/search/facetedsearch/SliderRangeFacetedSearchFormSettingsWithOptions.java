package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.RangeFacetResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface SliderRangeFacetedSearchFormSettingsWithOptions<T> extends SliderRangeFacetedSearchFormSettings<T>, FacetedSearchFormSettingsWithOptions<T, BucketRangeFacetedSearchFormOption> {

    static <T> SliderRangeFacetedSearchFormSettingsWithOptions<T> of(final SliderRangeFacetedSearchFormSettings<T> settings,
                                                                     final List<BucketRangeFacetedSearchFormOption> options) {
        return new SliderRangeFacetedSearchFormSettingsWithOptionsImpl<>(settings, options);
    }

    /**
     * Generates the facet options according to the facet result provided.
     * @return the generated facet options
     */
    static <T> SliderRangeFacetedSearchFormSettingsWithOptions<T> ofFacetResult(final SliderRangeFacetedSearchFormSettings<T> settings,
                                                                                final RangeFacetResult facetResult) {
        final List<BucketRangeFacetedSearchFormOption> options = facetResult.getRanges().stream()
                        .map(BucketRangeFacetedSearchFormOption::ofRangeStats)
                        .collect(toList());
        return of(settings, options);
    }
}
