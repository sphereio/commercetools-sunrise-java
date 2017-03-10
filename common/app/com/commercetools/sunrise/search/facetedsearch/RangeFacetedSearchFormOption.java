package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

public interface RangeFacetedSearchFormOption extends FacetedSearchFormOption {

    String getLowerEndpoint();

    String getUpperEndpoint();

    static RangeFacetedSearchFormOption of(final String fieldLabel, final String fieldValue, final List<String> value,
                                           final boolean isDefault, final long count, final String lowerEndpoint, final String upperEndpoint) {
        return new RangeFacetedSearchFormOptionImpl(fieldLabel, fieldValue, value, isDefault, count, lowerEndpoint, upperEndpoint);
    }
}
