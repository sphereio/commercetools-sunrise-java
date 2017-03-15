package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class ConfigurableFacetedSearchFormSettingsList<T> implements FacetedSearchFormSettingsList<T> {

    private final static String CONFIG_TYPE = "type";
    private final static String CONFIG_TYPE_TERM = "term";
    private final static String CONFIG_TYPE_SLIDER = "sliderRange";
    private final static String CONFIG_TYPE_BUCKET = "bucketRange";

    private final List<TermFacetedSearchFormSettings<T>> termSettings = new ArrayList<>();
    private final List<SliderRangeFacetedSearchFormSettings<T>> sliderRangeSettings = new ArrayList<>();
    private final List<BucketRangeFacetedSearchFormSettings<T>> bucketRangeSettings = new ArrayList<>();

    protected ConfigurableFacetedSearchFormSettingsList(final List<Configuration> configurations,
                                                        final List<? extends FacetMapperType> facetMapperTypes) {
        IntStream.range(0, configurations.size())
                .forEach(i -> {
                    final Configuration configuration = configurations.get(i);
                    final String type = Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                            .orElseThrow(() -> new SunriseConfigurationException("Could not find required facet type", CONFIG_TYPE, configuration));
                    initializeFacet(configuration, type, i, facetMapperTypes);
                });
    }

    @Override
    public List<TermFacetedSearchFormSettings<T>> getTermSettings() {
        return termSettings;
    }

    @Override
    public List<SliderRangeFacetedSearchFormSettings<T>> getSliderRangeSettings() {
        return sliderRangeSettings;
    }

    @Override
    public List<BucketRangeFacetedSearchFormSettings<T>> getBucketRangeSettings() {
        return bucketRangeSettings;
    }

    private void initializeFacet(final Configuration configuration, final String type, final int i,
                                 final List<? extends FacetMapperType> facetMapperTypes) {
        switch (type) {
            case CONFIG_TYPE_TERM:
                termSettings.add(new ConfigurableTermFacetedSearchFormSettings<>(configuration, i, facetMapperTypes));
                break;
            case CONFIG_TYPE_SLIDER:
                sliderRangeSettings.add(new ConfigurableSliderRangeFacetedSearchFormSettings<>(configuration, i, facetMapperTypes));
                break;
            case CONFIG_TYPE_BUCKET:
                bucketRangeSettings.add(new ConfigurableBucketRangeFacetedSearchFormSettings<>(configuration, i, facetMapperTypes));
                break;
            default:
                throw new SunriseConfigurationException("Unrecognized facet type \"" + type + "\"", CONFIG_TYPE, configuration);
        }
    }
}
