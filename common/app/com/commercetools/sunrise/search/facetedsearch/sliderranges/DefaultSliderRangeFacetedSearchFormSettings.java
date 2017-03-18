package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettings;

import java.util.Locale;

public class DefaultSliderRangeFacetedSearchFormSettings<T> extends AbstractFacetedSearchFormSettings<T, SimpleSliderRangeFacetedSearchFormSettings<T>> implements SliderRangeFacetedSearchFormSettings<T> {

    public DefaultSliderRangeFacetedSearchFormSettings(final SimpleSliderRangeFacetedSearchFormSettings<T> settings, final Locale locale) {
        super(settings, locale);
    }

    @Override
    public RangeEndpointFormSettings getLowerEndpointSettings() {
        return getSettings().getLowerEndpointSettings();
    }

    @Override
    public RangeEndpointFormSettings getUpperEndpointSettings() {
        return getSettings().getUpperEndpointSettings();
    }
}
