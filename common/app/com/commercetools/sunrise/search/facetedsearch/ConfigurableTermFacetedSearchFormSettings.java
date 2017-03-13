package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ConfigurableTermFacetedSearchFormSettings extends ConfigurableFacetedSearchFormSettings {

    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";

    protected ConfigurableTermFacetedSearchFormSettings(final Configuration configuration, final int position,
                                                        final List<? extends FacetUIType> facetUITypes, final List<? extends FacetMapperType> facetMapperTypes) {
        super(configuration, position, facetUITypes, facetMapperTypes);
        new TermFacetedSearchFormSettingsImpl()
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
