package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettings;

import javax.annotation.Nullable;

class SliderRangeFacetedSearchFormSettingsImpl<T> extends AbstractFacetedSearchFormSettings<T> implements SliderRangeFacetedSearchFormSettings<T> {

    private final RangeEndpointFormSettings lowerEndpointSettings;
    private final RangeEndpointFormSettings upperEndpointSettings;

    SliderRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                             final boolean isCountDisplayed, @Nullable final String uiType,
                                             final RangeEndpointFormSettings lowerEndpointSettings, final RangeEndpointFormSettings upperEndpointSettings) {
        super(fieldName, label, expression, isCountDisplayed, uiType);
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
