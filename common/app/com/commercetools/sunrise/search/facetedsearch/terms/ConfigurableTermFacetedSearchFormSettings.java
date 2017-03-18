package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettingsWithOptions;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.Optional;

public class ConfigurableTermFacetedSearchFormSettings<T> extends AbstractConfigurableFacetedSearchFormSettingsWithOptions<T> implements SimpleTermFacetedSearchFormSettings<T> {

    private static final String CONFIG_MAPPER = "mapper";
    private static final String CONFIG_MAPPER_NAME = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";
    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";

    private final String fieldName;
    @Nullable
    private final TermFacetMapperSettings mapperSettings;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    public ConfigurableTermFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
        this.fieldName = fieldName(configuration);
        this.mapperSettings = mapperSettings(configuration);
        this.limit = limit(configuration);
        this.threshold = threshold(configuration);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    @Nullable
    public TermFacetMapperSettings getMapperSettings() {
        return mapperSettings;
    }

    @Override
    @Nullable
    public Long getLimit() {
        return limit;
    }

    @Override
    @Nullable
    public Long getThreshold() {
        return threshold;
    }

    @Nullable
    private static TermFacetMapperSettings mapperSettings(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfig(CONFIG_MAPPER))
                .map(mapperConfig -> TermFacetMapperSettings.of(mapperName(mapperConfig), mapperConfig.getStringList(CONFIG_MAPPER_VALUES)))
                .orElse(null);
    }

    private static String mapperName(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_MAPPER_NAME))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find required type for the facet term mapper", CONFIG_MAPPER_NAME, configuration));
    }

    @Nullable
    private static Long limit(final Configuration configuration) {
        return configuration.getLong(CONFIG_LIMIT);
    }

    @Nullable
    private static Long threshold(final Configuration configuration) {
        return configuration.getLong(CONFIG_THRESHOLD);
    }
}
