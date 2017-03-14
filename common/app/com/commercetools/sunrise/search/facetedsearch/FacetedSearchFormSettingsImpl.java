package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

abstract class FacetedSearchFormSettingsImpl<T> extends AbstractFormFieldName implements FacetedSearchFormSettings<T> {

    private final String label;
    private final String expression;
    private final int position;
    @Nullable
    private final FacetUIType uiType;
    private final boolean isCountDisplayed;
    private final boolean isMultiSelect;
    private final boolean isMatchingAll;
    @Nullable
    private final FacetMapperSettings mapperSettings;

    FacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression, final int position,
                                  @Nullable final FacetUIType uiType, final boolean isCountDisplayed, final boolean isMultiSelect,
                                  final boolean isMatchingAll, @Nullable final FacetMapperSettings mapperSettings) {
        super(fieldName);
        this.label = label;
        this.expression = expression;
        this.position = position;
        this.uiType = uiType;
        this.isCountDisplayed = isCountDisplayed;
        this.isMultiSelect = isMultiSelect;
        this.isMatchingAll = isMatchingAll;
        this.mapperSettings = mapperSettings;
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
    public int getPosition() {
        return position;
    }

    @Nullable
    @Override
    public FacetUIType getUIType() {
        return uiType;
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
    public FacetMapperSettings getMapperSettings() {
        return mapperSettings;
    }
}
