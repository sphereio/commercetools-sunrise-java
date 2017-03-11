package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import com.commercetools.sunrise.search.facetedsearch.mappers.SunriseFacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public abstract class ConfigurableTermFacetedSearchFormSettings extends FacetedSearchFormSettingsImpl {

    private static final String CONFIG_KEY = "key";
    private static final String CONFIG_TYPE = "type";
    private static final String CONFIG_LABEL = "label";
    private static final String CONFIG_EXPR = "expr";
    private static final String CONFIG_COUNT = "count";
    private static final String CONFIG_MULTI_SELECT = "multiSelect";
    private static final String CONFIG_MATCHING_ALL = "matchingAll";
    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";

    private static final String CONFIG_MAPPER = "mapper";
    private static final String CONFIG_MAPPER_TYPE = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";

    protected ConfigurableTermFacetedSearchFormSettings(final Configuration configuration) {
        super(key(configuration), label(configuration), expression(configuration), type(configuration),
                limit(configuration), threshold(configuration), isCountDisplayed(configuration),
                isMultiSelect(configuration), isMatchingAll(configuration), mapper(configuration));
    }

    private static String key(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_KEY))
                .orElseThrow(() -> new SunriseConfigurationException("Missing key to create facet", CONFIG_KEY, configuration));
    }

    private static String label(final Configuration configuration) {
        return configuration.getString(CONFIG_LABEL, "");
    }

    private static SunriseFacetUIType type(final Configuration configuration) {
        final String type = configuration.getString(CONFIG_TYPE, "").toUpperCase();
        return Arrays.stream(SunriseFacetUIType.values())
                .filter(typeValue -> typeValue.name().equals(type))
                .findAny()
                .orElseThrow(() -> new SunriseConfigurationException("Unrecognized facet type \"" + type + "\"", CONFIG_TYPE, configuration));
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
    private static Long limit(final Configuration configuration) {
        return configuration.getLong(CONFIG_LIMIT);
    }

    @Nullable
    private static Long threshold(final Configuration configuration) {
        return configuration.getLong(CONFIG_THRESHOLD);
    }

    @Nullable
    private static FacetMapperSettings mapper(final Configuration configuration) {
        final Configuration mapperConfig = configuration.getConfig(CONFIG_MAPPER);
        if (mapperConfig != null) {
            final FacetMapperType type = mapperType(configuration);
            final List<String> values = mapperConfig.getStringList(CONFIG_MAPPER_VALUES, emptyList());
            return FacetMapperSettings.of(type, values);
        }
        return null;
    }

    private static SunriseFacetMapperType mapperType(final Configuration configuration) {
        final String type = configuration.getString(CONFIG_MAPPER_TYPE, "").toUpperCase();
        return Arrays.stream(SunriseFacetMapperType.values())
                .filter(typeValue -> typeValue.name().equals(type))
                .findAny()
                .orElseGet(() -> SunriseFacetMapperType.NONE);
    }
}
