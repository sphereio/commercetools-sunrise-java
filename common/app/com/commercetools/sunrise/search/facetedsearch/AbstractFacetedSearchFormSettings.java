package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormFieldName;

import javax.annotation.Nullable;

public abstract class AbstractFacetedSearchFormSettings<T> extends AbstractFormFieldName implements FacetedSearchFormSettings<T> {

    private final String label;
    private final String attributePath;
    private final boolean isCountDisplayed;
    @Nullable
    private final String uiType;

    protected AbstractFacetedSearchFormSettings(final String fieldName, final String label, final String attributePath,
                                                final boolean isCountDisplayed, @Nullable final String uiType) {
        super(fieldName);
        this.label = label;
        this.attributePath = attributePath;
        this.uiType = uiType;
        this.isCountDisplayed = isCountDisplayed;
    }

    @Override
    public String getFieldLabel() {
        return label;
    }

    @Override
    public String getAttributePath() {
        return attributePath;
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
