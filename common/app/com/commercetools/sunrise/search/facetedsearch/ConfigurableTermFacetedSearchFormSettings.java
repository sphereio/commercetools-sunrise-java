package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.List;

final class ConfigurableTermFacetedSearchFormSettings<T> extends ConfigurableFacetedSearchFormSettings<T> implements TermFacetedSearchFormSettings<T> {

    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";

    @Nullable
    private final Long limit;
    @Nullable
    private final Long threshold;

    ConfigurableTermFacetedSearchFormSettings(final Configuration configuration, final int position,
                                              final List<? extends FacetMapperType> facetMapperTypes) {
        super(configuration, position, facetMapperTypes);
        this.limit = limit(configuration);
        this.threshold = threshold(configuration);
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
    private static Long limit(final Configuration configuration) {
        return configuration.getLong(CONFIG_LIMIT);
    }

    @Nullable
    private static Long threshold(final Configuration configuration) {
        return configuration.getLong(CONFIG_THRESHOLD);
    }
}
