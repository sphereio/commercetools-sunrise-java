package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;

abstract class FacetedSearchFormSettingsImpl<T> extends AbstractFormFieldName implements FacetedSearchFormSettings<T> {

    private final String label;
    private final String attributePath;
    private final int position;
    private final boolean isCountDisplayed;
    private final boolean isMultiSelect;
    private final boolean isMatchingAll;
    @Nullable
    private final String uiType;
    @Nullable
    private final FacetMapperSettings mapperSettings;

    FacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath, final int position,
                                  final boolean isCountDisplayed, final boolean isMultiSelect, final boolean isMatchingAll,
                                  @Nullable final String uiType, @Nullable final FacetMapperSettings mapperSettings) {
        super(fieldName);
        this.label = label;
        this.attributePath = attributePath;
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
    public String getAttributePath() {
        return attributePath;
    }

    @Override
    public int getPosition() {
        return position;
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
    public String getUIType() {
        return uiType;
    }

    @Nullable
    @Override
    public FacetMapperSettings getMapperSettings() {
        return mapperSettings;
    }
}
