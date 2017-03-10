package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

final class RangeFacetedSearchFormOptionImpl extends FacetedSearchFormOptionImpl implements RangeFacetedSearchFormOption {

    private final String lowerEndpoint;
    private final String upperEndpoint;

    RangeFacetedSearchFormOptionImpl(final String fieldLabel, final String fieldValue, final List<String> value,
                                     final long count, final String lowerEndpoint, final String upperEndpoint) {
        super(fieldLabel, fieldValue, value, count);
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
    }

    @Override
    public String getLowerEndpoint() {
        return lowerEndpoint;
    }

    @Override
    public String getUpperEndpoint() {
        return upperEndpoint;
    }
}
