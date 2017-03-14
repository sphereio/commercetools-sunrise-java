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
    private final static String CONFIG_TYPE_RANGE = "range";

    private final List<TermFacetedSearchFormSettings<T>> termSettings = new ArrayList<>();
    private final List<RangeFacetedSearchFormSettings<T>> rangeSettings = new ArrayList<>();

    protected ConfigurableFacetedSearchFormSettingsList(final List<Configuration> configurations,
                                                        final List<? extends FacetUIType> facetUITypes,
                                                        final List<? extends FacetMapperType> facetMapperTypes) {
        IntStream.range(0, configurations.size())
                .forEach(i -> {
                    final Configuration configuration = configurations.get(i);
                    final String type = Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                            .orElseThrow(() -> new SunriseConfigurationException("Could not find required facet type", CONFIG_TYPE, configuration));
                    initializeFacet(configuration, type, i, facetUITypes, facetMapperTypes);
                });
    }

    @Override
    public List<TermFacetedSearchFormSettings<T>> getTermSettings() {
        return termSettings;
    }

    @Override
    public List<RangeFacetedSearchFormSettings<T>> getRangeSettings() {
        return rangeSettings;
    }

    private void initializeFacet(final Configuration configuration, final String type, final int i,
                                 final List<? extends FacetUIType> facetUITypes,
                                 final List<? extends FacetMapperType> facetMapperTypes) {
        switch (type) {
            case CONFIG_TYPE_TERM:
                termSettings.add(new ConfigurableTermFacetedSearchFormSettings<>(configuration, i, facetUITypes, facetMapperTypes));
                break;
            case CONFIG_TYPE_RANGE:
                rangeSettings.add(new ConfigurableRangeFacetedSearchFormSettings<>(configuration, i, facetUITypes, facetMapperTypes));
                break;
            default:
                throw new SunriseConfigurationException("Unrecognized facet type \"" + type + "\"", CONFIG_TYPE, configuration);
        }
    }
}
