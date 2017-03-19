package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.search.facetedsearch.AbstractSimpleFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;

class SimpleTermFacetedSearchFormSettingsImpl extends AbstractSimpleFacetedSearchFormSettingsWithOptions implements SimpleTermFacetedSearchFormSettings {

    private final String fieldName;
    @Nullable
    private final TermFacetMapperSettings mapperSettings;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    SimpleTermFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath,
                                            final boolean isCountDisplayed, @Nullable final String uiType,
                                            final boolean isMultiSelect, final boolean isMatchingAll,
                                            @Nullable final TermFacetMapperSettings mapperSettings,
                                            @Nullable final Long limit, @Nullable final Long threshold) {
        super(label, attributePath, isCountDisplayed, uiType, isMultiSelect, isMatchingAll);
        this.fieldName = fieldName;
        this.mapperSettings = mapperSettings;
        this.limit = limit;
        this.threshold = threshold;
    }

    @Override
    public String getFieldName() {
        return fieldName;
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
