package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

abstract class ConfigurableFacetedSearchFormSettings<T> extends FacetedSearchFormSettingsImpl<T> {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String CONFIG_UI_TYPE = "uiType";
    private static final String CONFIG_LABEL = "label";
    private static final String CONFIG_ATTRIBUTE_PATH = "attributePath";
    private static final String CONFIG_COUNT = "count";
    private static final String CONFIG_MULTI_SELECT = "multiSelect";
    private static final String CONFIG_MATCHING_ALL = "matchingAll";

    private static final String CONFIG_MAPPER = "mapper";
    private static final String CONFIG_MAPPER_TYPE = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";

    ConfigurableFacetedSearchFormSettings(final Configuration configuration, final int position,
                                          final List<? extends FacetMapperType> facetMapperTypes) {
        super(key(configuration), label(configuration), attributePath(configuration), position,
                isCountDisplayed(configuration), isMultiSelect(configuration), isMatchingAll(configuration),
                uiType(configuration), mapperSettings(configuration, facetMapperTypes));
    }

    private static String key(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_FIELD_NAME))
                .orElseThrow(() -> new SunriseConfigurationException("Missing required field name for facet", CONFIG_FIELD_NAME, configuration));
    }

    private static String label(final Configuration configuration) {
        return configuration.getString(CONFIG_LABEL, "");
    }

    private static String attributePath(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_ATTRIBUTE_PATH))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet attribute path expression", CONFIG_ATTRIBUTE_PATH, configuration));
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
    private static String uiType(final Configuration configuration) {
        return configuration.getString(CONFIG_UI_TYPE);
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
