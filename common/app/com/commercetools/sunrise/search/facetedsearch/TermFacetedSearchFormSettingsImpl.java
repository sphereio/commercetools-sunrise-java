package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

class TermFacetedSearchFormSettingsImpl extends FacetedSearchFormSettingsImpl implements TermFacetedSearchFormSettings {

    TermFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                      final FacetUIType type, @Nullable final Long limit, @Nullable final Long threshold,
                                      final boolean isCountDisplayed, final boolean isMultiSelect, final boolean isMatchingAll,
                                      @Nullable final FacetMapperSettings mapper) {
        super(fieldName, label, expression, type, limit, threshold, isCountDisplayed, isMultiSelect, isMatchingAll, mapper);
    }
}
