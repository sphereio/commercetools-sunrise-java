package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

public interface RangeFacetedSearchFormSettings extends FacetedSearchFormSettings {

    static RangeFacetedSearchFormSettings of(final String fieldName, final String label, final String expression, final FacetUIType facetType,
                                             @Nullable final Long limit, @Nullable final Long threshold, final boolean isCountDisplayed,
                                             final boolean isMultiSelect, final boolean isMatchingAll, @Nullable final FacetMapperSettings mapper) {
        return new RangeFacetedSearchFormSettingsImpl(fieldName, label, expression, facetType, limit, threshold, isCountDisplayed, isMultiSelect, isMatchingAll, mapper);
    }
}
