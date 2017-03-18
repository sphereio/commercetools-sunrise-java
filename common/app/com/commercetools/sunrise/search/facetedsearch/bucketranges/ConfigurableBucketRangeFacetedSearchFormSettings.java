package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettingsWithOptions;
import play.Configuration;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ConfigurableBucketRangeFacetedSearchFormSettings<T> extends AbstractConfigurableFacetedSearchFormSettingsWithOptions<T> implements SimpleBucketRangeFacetedSearchFormSettings<T> {

    private static final String CONFIG_RANGES = "ranges";

    private final String fieldName;
    private final List<BucketRangeFacetedSearchFormOption> options;

    public ConfigurableBucketRangeFacetedSearchFormSettings(final Configuration configuration,
                                                            final Function<Configuration, BucketRangeFacetedSearchFormOption> optionCreator) {
        super(configuration);
        this.fieldName = fieldName(configuration);
        this.options = options(configuration, optionCreator);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }

    private static List<BucketRangeFacetedSearchFormOption> options(final Configuration configuration,
                                                                    final Function<Configuration, BucketRangeFacetedSearchFormOption> optionCreator) {
        return configuration.getConfigList(CONFIG_RANGES, emptyList()).stream()
                .map(optionCreator)
                .collect(toList());
    }
}
