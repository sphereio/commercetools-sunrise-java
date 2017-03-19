package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractSimpleFacetedSearchFormSettings;

import javax.annotation.Nullable;

class SimpleSliderRangeFacetedSearchFormSettingsImpl extends AbstractSimpleFacetedSearchFormSettings implements SimpleSliderRangeFacetedSearchFormSettings {

    private final RangeEndpointFormSettings lowerEndpointSettings;
    private final RangeEndpointFormSettings upperEndpointSettings;

    SimpleSliderRangeFacetedSearchFormSettingsImpl(final String label, final String expression, final boolean isCountDisplayed,
                                                   @Nullable final String uiType, final RangeEndpointFormSettings lowerEndpointSettings,
                                                   final RangeEndpointFormSettings upperEndpointSettings) {
        super(label, expression, isCountDisplayed, uiType);
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
