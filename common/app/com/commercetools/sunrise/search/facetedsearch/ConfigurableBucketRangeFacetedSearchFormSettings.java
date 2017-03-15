package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

final class ConfigurableBucketRangeFacetedSearchFormSettings<T> extends ConfigurableFacetedSearchFormSettings<T> implements BucketRangeFacetedSearchFormSettings<T> {

    private static final String CONFIG_RANGES = "ranges";

    private final List<BucketRangeFacetedSearchFormOption> options;

    protected ConfigurableBucketRangeFacetedSearchFormSettings(final Configuration configuration, final int position,
                                                               final List<? extends FacetMapperType> facetMapperTypes) {
        super(configuration, position, facetMapperTypes);
        this.options = options(configuration);
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }

    private static List<BucketRangeFacetedSearchFormOption> options(final Configuration configuration) {
        return configuration.getConfigList(CONFIG_RANGES, emptyList()).stream()
                .map(rangeConfiguration -> new ConfigurableBucketRangeFacetedSearchFormOption(configuration))
                .collect(toList());
    }
}
