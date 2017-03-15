package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapperSettings;

import javax.annotation.Nullable;

class TermFacetedSearchFormSettingsImpl<T> extends MultiOptionFacetedSearchFormSettingsImpl<T> implements TermFacetedSearchFormSettings<T> {

    @Nullable
    private final TermFacetMapperSettings mapperSettings;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    TermFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath,
                                      final int position, final boolean isCountDisplayed, @Nullable final String uiType,
                                      final boolean isMultiSelect, final boolean isMatchingAll,
                                      @Nullable final TermFacetMapperSettings mapperSettings,
                                      @Nullable final Long limit, @Nullable final Long threshold) {
        super(fieldName, label, attributePath, position, isCountDisplayed, uiType, isMultiSelect, isMatchingAll);
        this.mapperSettings = mapperSettings;
        this.limit = limit;
        this.threshold = threshold;
    }

    @Override
    @Nullable
    public TermFacetMapperSettings getMapperSettings() {
        return mapperSettings;
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
