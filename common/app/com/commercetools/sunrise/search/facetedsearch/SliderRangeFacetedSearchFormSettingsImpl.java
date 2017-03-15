package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;

class SliderRangeFacetedSearchFormSettingsImpl<T> extends FacetedSearchFormSettingsImpl<T> implements SliderRangeFacetedSearchFormSettings<T> {

    private final RangeEndpointFormSettings lowerEndpointSettings;
    private final RangeEndpointFormSettings upperEndpointSettings;

    SliderRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                             final int position, final boolean isCountDisplayed, @Nullable final String uiType,
                                             final RangeEndpointFormSettings lowerEndpointSettings, final RangeEndpointFormSettings upperEndpointSettings) {
        super(fieldName, label, expression, position, isCountDisplayed, false, true, uiType, null);
        this.lowerEndpointSettings = lowerEndpointSettings;
        this.upperEndpointSettings = upperEndpointSettings;
    }

    @Override
    public RangeEndpointFormSettings getLowerEndpointSettings() {
        return lowerEndpointSettings;
    }

    @Override
    public RangeEndpointFormSettings getUpperEndpointSettings() {
        return upperEndpointSettings;
    }
}
