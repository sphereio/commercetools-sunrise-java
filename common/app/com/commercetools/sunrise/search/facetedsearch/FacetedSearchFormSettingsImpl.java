package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;
import io.sphere.sdk.facets.FacetType;

import javax.annotation.Nullable;

class FacetedSearchFormSettingsImpl extends AbstractFormFieldName implements FacetedSearchFormSettings {

    private final FacetType facetType;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;
    private final boolean isCountDisplayed;
    private final boolean isMultiSelect;
    private final boolean isMatchingAll;
    @Nullable
    private final TermFacetResultMapper mapper;

    FacetedSearchFormSettingsImpl(final String fieldName, final FacetType facetType, @Nullable final Long limit,
                                  @Nullable final Long threshold, final boolean isCountDisplayed, final boolean isMultiSelect,
                                  final boolean isMatchingAll, @Nullable final TermFacetResultMapper mapper) {
        super(fieldName);
        this.facetType = facetType;
        this.limit = limit;
        this.threshold = threshold;
        this.isCountDisplayed = isCountDisplayed;
        this.isMultiSelect = isMultiSelect;
        this.isMatchingAll = isMatchingAll;
        this.mapper = mapper;
    }

    @Override
    public FacetType getType() {
        return facetType;
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

    @Override
    public boolean isCountDisplayed() {
        return isCountDisplayed;
    }

    @Override
    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    @Override
    public boolean isMatchingAll() {
        return isMatchingAll;
    }

    @Override
    @Nullable
    public TermFacetResultMapper getMapper() {
        return mapper;
    }
}
