package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;

import javax.annotation.Nullable;

abstract class FacetedSearchFormSettingsImpl<T> extends AbstractFormFieldName implements FacetedSearchFormSettings<T> {

    private final String label;
    private final String attributePath;
    private final int position;
    private final boolean isCountDisplayed;
    @Nullable
    private final String uiType;

    FacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath, final int position,
                                  final boolean isCountDisplayed, @Nullable final String uiType) {
        super(fieldName);
        this.label = label;
        this.attributePath = attributePath;
        this.position = position;
        this.uiType = uiType;
        this.isCountDisplayed = isCountDisplayed;
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

    @Nullable
    @Override
    public String getUIType() {
        return uiType;
    }
}
