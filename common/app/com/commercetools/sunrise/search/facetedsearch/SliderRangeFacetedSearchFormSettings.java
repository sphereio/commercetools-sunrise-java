package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.RangeFacetResult;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface SliderRangeFacetedSearchFormSettings<T> extends RangeFacetedSearchFormSettings<T> {

    @Nullable
    String getLowerEndpoint();

    @Nullable
    String getUpperEndpoint();

    String getMaxValue();

    String getMinValue();

    static <T> SliderRangeFacetedSearchFormSettings<T> of(final RangeFacetedSearchFormSettings<T> settings,
                                                          final List<BucketRangeFacetedSearchFormOption> options) {
        return new RangeFacetedSearchFormSettingsWithOptionsImpl<>(settings, options);
    }

    /**
     * Generates the facet options according to the facet result provided.
     * @return the generated facet options
     */
    static <T> SliderRangeFacetedSearchFormSettings<T> ofFacetResult(final RangeFacetedSearchFormSettings<T> settings,
                                                                     final RangeFacetResult facetResult) {
        final List<BucketRangeFacetedSearchFormOption> options = facetResult.getRanges().stream()
                        .map(BucketRangeFacetedSearchFormOption::ofRangeStats)
                        .collect(toList());
        return of(settings, options);
    }
}
