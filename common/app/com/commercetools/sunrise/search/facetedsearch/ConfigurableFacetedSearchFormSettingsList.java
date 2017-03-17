package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ConfigurableFacetedSearchFormSettingsList<T> implements FacetedSearchFormSettingsList<T> {

    private final static String CONFIG_TYPE = "type";
    private final static String CONFIG_TYPE_TERM = "term";
    private final static String CONFIG_TYPE_SLIDER = "sliderRange";
    private final static String CONFIG_TYPE_BUCKET = "bucketRange";

    private final List<PositionedSettings<TermFacetedSearchFormSettings<T>>> termSettings = new ArrayList<>();
    private final List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> sliderRangeSettings = new ArrayList<>();
    private final List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> bucketRangeSettings = new ArrayList<>();

    public ConfigurableFacetedSearchFormSettingsList(final List<Configuration> configurations,
                                                     final Function<Configuration, TermFacetedSearchFormSettings<T>> termSettingsCreator,
                                                     final Function<Configuration, SliderRangeFacetedSearchFormSettings<T>> sliderRangeSettingsCreator,
                                                     final Function<Configuration, BucketRangeFacetedSearchFormSettings<T>> bucketRangeSettingsCreator) {
        IntStream.range(0, configurations.size())
                .forEach(i -> {
                    final Configuration configuration = configurations.get(i);
                    final String type = Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                            .orElseThrow(() -> new SunriseConfigurationException("Could not find required facet type", CONFIG_TYPE, configuration));
                    initializeFacet(configuration, type, i, termSettingsCreator, sliderRangeSettingsCreator, bucketRangeSettingsCreator);
                });
    }

    @Override
    public List<PositionedSettings<TermFacetedSearchFormSettings<T>>> getTermSettings() {
        return termSettings;
    }

    @Override
    public List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> getSliderRangeSettings() {
        return sliderRangeSettings;
    }

    @Override
    public List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> getBucketRangeSettings() {
        return bucketRangeSettings;
    }

    private void initializeFacet(final Configuration configuration, final String type, final int position,
                                 final Function<Configuration, TermFacetedSearchFormSettings<T>> termSettingsCreator,
                                 final Function<Configuration, SliderRangeFacetedSearchFormSettings<T>> sliderRangeSettingsCreator,
                                 final Function<Configuration, BucketRangeFacetedSearchFormSettings<T>> bucketRangeSettingsCreator) {
        switch (type) {
            case CONFIG_TYPE_TERM:
                termSettings.add(PositionedSettings.of(position, termSettingsCreator.apply(configuration)));
                break;
            case CONFIG_TYPE_SLIDER:
                sliderRangeSettings.add(PositionedSettings.of(position, sliderRangeSettingsCreator.apply(configuration)));
                break;
            case CONFIG_TYPE_BUCKET:
                bucketRangeSettings.add(PositionedSettings.of(position, bucketRangeSettingsCreator.apply(configuration)));
                break;
            default:
                throw new SunriseConfigurationException("Unrecognized facet type \"" + type + "\"", CONFIG_TYPE, configuration);
        }
    }
}
