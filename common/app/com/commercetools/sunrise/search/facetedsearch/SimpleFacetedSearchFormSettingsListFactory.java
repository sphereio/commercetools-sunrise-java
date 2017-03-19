package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.ConfigurableBucketRangeFacetedSearchFormOption;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.ConfigurableBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.ConfigurableSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.ConfigurableTermFacetedSearchFormSettings;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SimpleFacetedSearchFormSettingsListFactory extends SunriseModel {

    private final static String CONFIG_TYPE = "type";
    private final static String CONFIG_TYPE_TERM = "term";
    private final static String CONFIG_TYPE_SLIDER = "sliderRange";
    private final static String CONFIG_TYPE_BUCKET = "bucketRange";

    private final List<Configuration> configurationList;

    protected SimpleFacetedSearchFormSettingsListFactory(final List<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    public SimpleFacetedSearchFormSettingsList create() {
        final List<SimpleFacetedSearchFormSettings> list = new ArrayList<>();
        configurationList.forEach(configuration -> addSettingsToList(list, configuration, type(configuration)));
        return SimpleFacetedSearchFormSettingsList.of(list);
    }

    protected void addSettingsToList(final List<SimpleFacetedSearchFormSettings> list, final Configuration configuration, final String type) {
        switch (type) {
            case CONFIG_TYPE_TERM:
                list.add(createTermSettings(configuration));
                break;
            case CONFIG_TYPE_SLIDER:
                list.add(createSliderRangeSettings(configuration));
                break;
            case CONFIG_TYPE_BUCKET:
                list.add(createBucketRangeSettings(configuration));
                break;
            default:
                throw new SunriseConfigurationException("Unknown facet type \"" + type + "\"", CONFIG_TYPE, configuration);
        }
    }

    protected SimpleFacetedSearchFormSettings createTermSettings(final Configuration configuration) {
        return new ConfigurableTermFacetedSearchFormSettings(configuration);
    }

    protected SimpleFacetedSearchFormSettings createSliderRangeSettings(final Configuration configuration) {
        return new ConfigurableSliderRangeFacetedSearchFormSettings(configuration);
    }

    protected SimpleFacetedSearchFormSettings createBucketRangeSettings(final Configuration configuration) {
        return new ConfigurableBucketRangeFacetedSearchFormSettings(configuration, ConfigurableBucketRangeFacetedSearchFormOption::new);
    }

    private String type(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find required facet type", CONFIG_TYPE, configuration));
    }
}
