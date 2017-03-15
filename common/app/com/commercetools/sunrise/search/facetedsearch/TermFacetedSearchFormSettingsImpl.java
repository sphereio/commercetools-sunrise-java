package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

class TermFacetedSearchFormSettingsImpl<T> extends FacetedSearchFormSettingsImpl<T> implements TermFacetedSearchFormSettings<T> {

    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    TermFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath, final int position,
                                      final boolean isCountDisplayed, final boolean isMultiSelect, final boolean isMatchingAll,
                                      @Nullable final String uiType, @Nullable final FacetMapperSettings mapper,
                                      @Nullable final Long limit, @Nullable final Long threshold) {
        super(fieldName, label, attributePath, position, isCountDisplayed, isMultiSelect, isMatchingAll, uiType, mapper);
        this.limit = limit;
        this.threshold = threshold;
    }

    @Override
    @Nullable
    public Long getLimit() {
        return limit;
    }

    @Override
    @Nullable
    public Long getThreshold() {
        return threshold;
    }
}
