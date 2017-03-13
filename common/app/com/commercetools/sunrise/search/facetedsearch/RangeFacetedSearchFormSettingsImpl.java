package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

class RangeFacetedSearchFormSettingsImpl extends FacetedSearchFormSettingsImpl implements RangeFacetedSearchFormSettings {

    RangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression, final int position,
                                       final FacetUIType type, final boolean isCountDisplayed, final boolean isMultiSelect,
                                       final boolean isMatchingAll, @Nullable final FacetMapperSettings mapper) {
        super(fieldName, label, expression, position, type, isCountDisplayed, isMultiSelect, isMatchingAll, mapper);
    }
}
