package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class ConfigurableFacetedSearchFormSettings extends FacetedSearchFormSettingsImpl {

    private static final String CONFIG_KEY = "key";
    private static final String CONFIG_TYPE = "type";
    private static final String CONFIG_LABEL = "label";
    private static final String CONFIG_EXPR = "expr";
    private static final String CONFIG_COUNT = "count";
    private static final String CONFIG_MULTI_SELECT = "multiSelect";
    private static final String CONFIG_MATCHING_ALL = "matchingAll";

    private static final String CONFIG_MAPPER = "mapper";
    private static final String CONFIG_MAPPER_TYPE = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";

    protected ConfigurableFacetedSearchFormSettings(final Configuration configuration, final int position,
                                                    final List<? extends FacetUIType> facetUITypes, final List<? extends FacetMapperType> facetMapperTypes) {
        super(key(configuration), label(configuration), expression(configuration), position,
                type(configuration, facetUITypes), isCountDisplayed(configuration), isMultiSelect(configuration),
                isMatchingAll(configuration), mapperSettings(configuration, facetMapperTypes));
    }

    private static String key(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_KEY))
                .orElseThrow(() -> new SunriseConfigurationException("Missing key to create facet", CONFIG_KEY, configuration));
    }

    private static String label(final Configuration configuration) {
        return configuration.getString(CONFIG_LABEL, "");
    }

    private static FacetUIType type(final Configuration configuration, final List<? extends FacetUIType> facetUITypes) {
        return Optional.ofNullable(configuration.getString(CONFIG_TYPE))
                .map(type -> facetUITypes.stream()
                        .filter(typeValue -> typeValue.value().equals(type))
                        .findAny()
                        .orElseThrow(() -> new SunriseConfigurationException("Unrecognized facet UI type \"" + type + "\"", CONFIG_TYPE, configuration)))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find facet UI type required to initialize it", CONFIG_KEY, configuration));
    }

    private static String expression(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_EXPR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet attribute path expression", CONFIG_EXPR, configuration));
    }

    private static Boolean isCountDisplayed(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_COUNT, true);
    }

    private static Boolean isMatchingAll(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_MATCHING_ALL, false);
    }

    private static Boolean isMultiSelect(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_MULTI_SELECT, true);
    }

    @Nullable
    private static FacetMapperSettings mapperSettings(final Configuration configuration, final List<? extends FacetMapperType> facetMapperTypes) {
        return Optional.ofNullable(configuration.getConfig(CONFIG_MAPPER))
                .flatMap(mapperConfig -> mapperType(configuration, facetMapperTypes)
                        .map(type -> FacetMapperSettings.of(type, mapperConfig.getStringList(CONFIG_MAPPER_VALUES))))
                .orElse(null);
    }

    private static Optional<? extends FacetMapperType> mapperType(final Configuration configuration, final List<? extends FacetMapperType> facetMapperTypes) {
        return Optional.ofNullable(configuration.getString(CONFIG_MAPPER_TYPE))
                .flatMap(type -> facetMapperTypes.stream()
                        .filter(typeValue -> typeValue.value().equals(type))
                        .findAny());
    }
}
