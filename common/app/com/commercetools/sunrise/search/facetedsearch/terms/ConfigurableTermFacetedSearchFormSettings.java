package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ConfigurableTermFacetedSearchFormSettings<T> extends AbstractConfigurableFacetedSearchFormSettingsWithOptions<T> implements TermFacetedSearchFormSettings<T> {

    private static final String CONFIG_MAPPER = "mapper";
    private static final String CONFIG_MAPPER_TYPE = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";
    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";

    @Nullable
    private final TermFacetMapperSettings mapperSettings;
    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    public ConfigurableTermFacetedSearchFormSettings(final Configuration configuration,
                                                     final List<? extends TermFacetMapperType> facetMapperTypes) {
        super(configuration);
        this.mapperSettings = mapperSettings(configuration, facetMapperTypes);
        this.limit = limit(configuration);
        this.threshold = threshold(configuration);
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
    private static TermFacetMapperSettings mapperSettings(final Configuration configuration, final List<? extends TermFacetMapperType> facetMapperTypes) {
        return Optional.ofNullable(configuration.getConfig(CONFIG_MAPPER))
                .flatMap(mapperConfig -> mapperType(mapperConfig, facetMapperTypes)
                        .map(type -> TermFacetMapperSettings.of(type, mapperConfig.getStringList(CONFIG_MAPPER_VALUES))))
                .orElse(null);
    }

    private static Optional<? extends TermFacetMapperType> mapperType(final Configuration configuration, final List<? extends TermFacetMapperType> facetMapperTypes) {
        return Optional.ofNullable(configuration.getString(CONFIG_MAPPER_TYPE))
                .flatMap(type -> facetMapperTypes.stream()
                        .filter(typeValue -> typeValue.value().equals(type))
                        .findAny());
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
