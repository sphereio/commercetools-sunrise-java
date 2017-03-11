package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

class FacetedSearchFormSettingsImpl extends AbstractFormFieldName implements FacetedSearchFormSettings {

    private final String label;
    private final String expression;
    private final FacetUIType type;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;
    private final boolean isCountDisplayed;
    private final boolean isMultiSelect;
    private final boolean isMatchingAll;
    @Nullable
    private final FacetMapperSettings mapper;

    FacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                  final FacetUIType type, @Nullable final Long limit, @Nullable final Long threshold,
                                  final boolean isCountDisplayed, final boolean isMultiSelect, final boolean isMatchingAll,
                                  @Nullable final FacetMapperSettings mapper) {
        super(fieldName);
        this.label = label;
        this.expression = expression;
        this.type = type;
        this.limit = limit;
        this.threshold = threshold;
        this.isCountDisplayed = isCountDisplayed;
        this.isMultiSelect = isMultiSelect;
        this.isMatchingAll = isMatchingAll;
        this.mapper = mapper;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public FacetUIType getType() {
        return type;
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

    @Nullable
    @Override
    public FacetMapperSettings getMapper() {
        return mapper;
    }
}
