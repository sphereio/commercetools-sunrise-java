package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class ConfigurableFacetedSearchFormSettingsList<T> implements SimpleFacetedSearchFormSettingsList<T> {

    private final static String CONFIG_TYPE = "type";
    private final static String CONFIG_TYPE_TERM = "term";
    private final static String CONFIG_TYPE_SLIDER = "sliderRange";
    private final static String CONFIG_TYPE_BUCKET = "bucketRange";

    private final List<PositionedSettings<SimpleTermFacetedSearchFormSettings<T>>> termSettings = new ArrayList<>();
    private final List<PositionedSettings<SimpleSliderRangeFacetedSearchFormSettings<T>>> sliderRangeSettings = new ArrayList<>();
    private final List<PositionedSettings<SimpleBucketRangeFacetedSearchFormSettings<T>>> bucketRangeSettings = new ArrayList<>();

    public ConfigurableFacetedSearchFormSettingsList(final List<Configuration> configurations) {
        IntStream.range(0, configurations.size())
                .forEach(i -> {
                    final Configuration configuration = configurations.get(i);
                    final String type = Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                            .orElseThrow(() -> new SunriseConfigurationException("Could not find required facet type", CONFIG_TYPE, configuration));
                    initializeFacet(configuration, type, i);
                });
    }

    @Override
    public List<PositionedSettings<SimpleTermFacetedSearchFormSettings<T>>> getSimpleTermSettings() {
        return termSettings;
    }

    @Override
    public List<PositionedSettings<SimpleSliderRangeFacetedSearchFormSettings<T>>> getSimpleSliderRangeSettings() {
        return sliderRangeSettings;
    }

    @Override
    public List<PositionedSettings<SimpleBucketRangeFacetedSearchFormSettings<T>>> getSimpleBucketRangeSettings() {
        return bucketRangeSettings;
    }

    private void initializeFacet(final Configuration configuration, final String type, final int position) {
        switch (type) {
            case CONFIG_TYPE_TERM:
                termSettings.add(PositionedSettings.of(position, createTermSettings(configuration)));
                break;
            case CONFIG_TYPE_SLIDER:
                sliderRangeSettings.add(PositionedSettings.of(position, createSliderRangeSettings(configuration)));
                break;
            case CONFIG_TYPE_BUCKET:
                bucketRangeSettings.add(PositionedSettings.of(position, createBucketRangeSettings(configuration)));
                break;
            default:
                handleUnknownFacet(configuration, type, position);
        }
    }

    protected void handleUnknownFacet(final Configuration configuration, final String type, final int position) {
        throw new SunriseConfigurationException("Unrecognized facet type \"" + type + "\"", CONFIG_TYPE, configuration);
    }

    protected abstract SimpleTermFacetedSearchFormSettings<T> createTermSettings(final Configuration configuration);

    protected abstract SimpleSliderRangeFacetedSearchFormSettings<T> createSliderRangeSettings(final Configuration configuration);

    protected abstract SimpleBucketRangeFacetedSearchFormSettings<T> createBucketRangeSettings(final Configuration configuration);
}
