package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.facets.RangeOption;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeStats;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public interface RangeFacetedSearchFormOptionOld extends FacetedSearchFormOption {

    @Nullable
    String getLowerEndpoint();

    @Nullable
    String getUpperEndpoint();

    String getMaxValue();

    String getMinValue();

    static RangeFacetedSearchFormOptionOld of(final String fieldLabel, final String fieldValue, final String value,
                                              final long count, final String lowerEndpoint, final String upperEndpoint,
                                              final String minValue, final String maxValue) {
        return new BucketRangeFacetedSearchFormOptionImpl(fieldLabel, fieldValue, value, count, lowerEndpoint, upperEndpoint, minValue, maxValue);
    }

    static RangeFacetedSearchFormOptionOld ofRangeStats(final RangeStats rangeStats) {
        final long count = firstNonNull(rangeStats.getProductCount(), 0L);
        return of("fieldlabel", "fieldvalue", "value", count, rangeStats.getLowerEndpoint(), rangeStats.getUpperEndpoint(), rangeStats.getMin(), rangeStats.getMax());
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    default List<RangeOption> initializeOptions(final List<FilterRange<String>> selectedValue, @Nullable final RangeFacetResult facetResult) {
        return Optional.ofNullable(facetResult)
                .map(result -> result.getRanges().stream()
                        .map(rangeTermStats -> RangeOption.of(rangeTermStats.getLowerEndpoint() != null ? rangeTermStats.getLowerEndpoint().replaceAll("\\..+","") : null,
                                rangeTermStats.getUpperEndpoint() != null ? rangeTermStats.getUpperEndpoint().replaceAll("\\..+","") : null,
                                rangeTermStats.getCount() , selectedValue != null ? checkIfSelected(rangeTermStats, selectedValue) : false))
                        .filter(range -> range.getCount() > 0)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    default Boolean checkIfSelected(RangeStats rangeTermStats, List<FilterRange<String>> selectedValue) {
        return rangeTermStats.getUpperEndpoint() != null && rangeTermStats.getLowerEndpoint() != null ?
                selectedValue.contains(FilterRange.of(rangeTermStats.getLowerEndpoint().replaceAll("\\..+",""),
                        rangeTermStats.getUpperEndpoint().replaceAll("\\..+",""))) : checkIfSelectedUnboundRange(rangeTermStats, selectedValue);
    }

    default Boolean checkIfSelectedUnboundRange(RangeStats rangeTermStats, List<FilterRange<String>> selectedValue) {
        return rangeTermStats.getLowerEndpoint() == null ? selectedValue.contains(FilterRange.atMost(rangeTermStats.getUpperEndpoint().replaceAll("\\..+","")))
                : selectedValue.contains(FilterRange.atLeast(rangeTermStats.getLowerEndpoint().replaceAll("\\..+","")));
    }
}
