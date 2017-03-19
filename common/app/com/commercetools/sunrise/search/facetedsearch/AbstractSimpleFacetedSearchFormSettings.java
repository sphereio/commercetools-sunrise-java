package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseModel;

import javax.annotation.Nullable;

public abstract class AbstractSimpleFacetedSearchFormSettings extends SunriseModel implements SimpleFacetedSearchFormSettings {

    private final String label;
    private final String attributePath;
    private final boolean isCountDisplayed;
    @Nullable
    private final String uiType;

    protected AbstractSimpleFacetedSearchFormSettings(final String label, final String attributePath,
                                                      final boolean isCountDisplayed, @Nullable final String uiType) {
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
