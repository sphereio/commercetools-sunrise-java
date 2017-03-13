package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;

final class RangeFacetedSearchFormOptionImpl extends FacetedSearchFormOptionImpl implements RangeFacetedSearchFormOption {

    private final String lowerEndpoint;
    private final String upperEndpoint;
    private final String minValue;
    private final String maxValue;

    RangeFacetedSearchFormOptionImpl(final String fieldLabel, final String fieldValue, final String value, final long count,
                                     final String lowerEndpoint, final String upperEndpoint, final String minValue, final String maxValue) {
        super(fieldLabel, fieldValue, value, count);
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Nullable
    @Override
    public String getLowerEndpoint() {
        return lowerEndpoint;
    }

    @Nullable
    @Override
    public String getUpperEndpoint() {
        return upperEndpoint;
    }

    @Override
    public String getMinValue() {
        return minValue;
    }

    @Override
    public String getMaxValue() {
        return maxValue;
    }
}
