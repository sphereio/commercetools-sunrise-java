package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettings;
import play.Configuration;

import java.util.Optional;

public class ConfigurableSliderRangeFacetedSearchFormSettings<T> extends AbstractConfigurableFacetedSearchFormSettings<T> implements SliderRangeFacetedSearchFormSettings<T> {

    private static final String CONFIG_LOWER = "lowerEndpoint";
    private static final String CONFIG_UPPER = "upperEndpoint";
    private static final String CONFIG_ENDPOINT_FIELD_NAME = "fieldName";

    private final RangeEndpointFormSettings lowerEndpointSettings;
    private final RangeEndpointFormSettings upperEndpointSettings;

    public ConfigurableSliderRangeFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
        this.lowerEndpointSettings = lowerEndpointSettings(configuration);
        this.upperEndpointSettings = upperEndpointSettings(configuration);
    }

    @Override
    public RangeEndpointFormSettings getLowerEndpointSettings() {
        return lowerEndpointSettings;
    }

    @Override
    public RangeEndpointFormSettings getUpperEndpointSettings() {
        return upperEndpointSettings;
    }

    private static RangeEndpointFormSettings lowerEndpointSettings(final Configuration configuration) {
        return endpointSettings(Optional.ofNullable(configuration.getConfig(CONFIG_LOWER))
                .orElseThrow(() -> new SunriseConfigurationException("Missing required lower endpoint configuration", CONFIG_LOWER, configuration)));
    }

    private static RangeEndpointFormSettings upperEndpointSettings(final Configuration configuration) {
        return endpointSettings(Optional.ofNullable(configuration.getConfig(CONFIG_UPPER))
                .orElseThrow(() -> new SunriseConfigurationException("Missing required upper endpoint configuration", CONFIG_UPPER, configuration)));
    }

    private static RangeEndpointFormSettings endpointSettings(final Configuration configuration) {
        final String fieldName = Optional.ofNullable(configuration.getString(CONFIG_ENDPOINT_FIELD_NAME))
                .orElseThrow(() -> new SunriseConfigurationException("Missing required field name for slider range facet endpoint", CONFIG_ENDPOINT_FIELD_NAME, configuration));
        return RangeEndpointFormSettings.of(fieldName);
    }
}
