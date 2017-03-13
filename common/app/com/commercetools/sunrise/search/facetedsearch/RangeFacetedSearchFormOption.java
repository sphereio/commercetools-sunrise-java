package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.model.RangeStats;

import javax.annotation.Nullable;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public interface RangeFacetedSearchFormOption extends FacetedSearchFormOption {

    @Nullable
    String getLowerEndpoint();

    @Nullable
    String getUpperEndpoint();

    String getMaxValue();

    String getMinValue();

    static RangeFacetedSearchFormOption of(final String fieldLabel, final String fieldValue, final String value,
                                           final long count, final String lowerEndpoint, final String upperEndpoint,
                                           final String minValue, final String maxValue) {
        return new RangeFacetedSearchFormOptionImpl(fieldLabel, fieldValue, value, count, lowerEndpoint, upperEndpoint, minValue, maxValue);
    }

    static RangeFacetedSearchFormOption ofRangeStats(final RangeStats rangeStats) {
        final long count = firstNonNull(rangeStats.getProductCount(), 0L);
        return of("fieldlabel", "fieldvalue", "value", count, rangeStats.getLowerEndpoint(), rangeStats.getUpperEndpoint(), rangeStats.getMin(), rangeStats.getMax());
    }
}
