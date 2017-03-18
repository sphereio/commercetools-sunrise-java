package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.SimpleFacetedSearchFormSettings;

import javax.annotation.Nullable;

public interface SimpleSliderRangeFacetedSearchFormSettings<T> extends SimpleFacetedSearchFormSettings<T> {

    RangeEndpointFormSettings getLowerEndpointSettings();

    RangeEndpointFormSettings getUpperEndpointSettings();

    static <T> SimpleSliderRangeFacetedSearchFormSettings<T> of(final String label, final String attributePath,
                                                                final boolean isCountDisplayed, @Nullable final String uiType,
                                                                final RangeEndpointFormSettings lowerEndpointSettings,
                                                                final RangeEndpointFormSettings upperEndpointSettings) {
        return new SimpleSliderRangeFacetedSearchFormSettingsImpl<>(label, attributePath, isCountDisplayed, uiType, lowerEndpointSettings, upperEndpointSettings);
    }
}
