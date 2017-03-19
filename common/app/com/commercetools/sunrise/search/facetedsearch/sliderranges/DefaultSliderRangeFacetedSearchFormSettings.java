package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettings;

import java.util.Locale;

public class DefaultSliderRangeFacetedSearchFormSettings<T> extends AbstractFacetedSearchFormSettings<SimpleSliderRangeFacetedSearchFormSettings> implements SliderRangeFacetedSearchFormSettings<T> {

    public DefaultSliderRangeFacetedSearchFormSettings(final SimpleSliderRangeFacetedSearchFormSettings settings, final Locale locale) {
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
