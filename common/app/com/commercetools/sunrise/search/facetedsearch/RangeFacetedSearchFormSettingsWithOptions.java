package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.RangeFacetResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface RangeFacetedSearchFormSettingsWithOptions<T> extends RangeFacetedSearchFormSettings<T>, FacetedSearchFormSettingsWithOptions<T, RangeFacetedSearchFormOption> {

    static <T> RangeFacetedSearchFormSettingsWithOptions<T> of(final RangeFacetedSearchFormSettings<T> settings,
                                                               final List<RangeFacetedSearchFormOption> options) {
        return new RangeFacetedSearchFormSettingsWithOptionsImpl<>(settings, options);
    }

    /**
     * Generates the facet options according to the facet result provided.
     * @return the generated facet options
     */
    static <T> RangeFacetedSearchFormSettingsWithOptions<T> ofFacetResult(final RangeFacetedSearchFormSettings<T> settings,
                                                                          final RangeFacetResult facetResult) {
        final List<RangeFacetedSearchFormOption> options = facetResult.getRanges().stream()
                        .map(RangeFacetedSearchFormOption::ofRangeStats)
                        .collect(toList());
        return of(settings, options);
    }
}
