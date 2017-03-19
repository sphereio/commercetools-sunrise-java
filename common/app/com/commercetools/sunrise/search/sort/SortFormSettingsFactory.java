package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.SunriseModel;
import play.Configuration;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class SortFormSettingsFactory extends SunriseModel {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String DEFAULT_FIELD_NAME = "sort";
    private static final String CONFIG_OPTIONS = "options";

    public SortFormSettingsFactory(final Configuration configuration, final Function<Configuration, SortFormOption> optionCreator) {
        super(fieldName(configuration), options(configuration, optionCreator));
    }

    public SortFormSettings<T> create() {

    }

    private static String fieldName(final Configuration configuration) {
        return configuration.getString(CONFIG_FIELD_NAME, DEFAULT_FIELD_NAME);
    }

    protected List<SortFormOption> options(final Configuration configuration) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(this::createOption)
                .collect(toList());
    }

    protected SortFormOption createOption(final Configuration configuration) {
        return new ConfigurableSortFormOption(configuration);
    }
}
