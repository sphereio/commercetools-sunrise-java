package com.commercetools.sunrise.search.sort;

import play.Configuration;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ConfigurableSortFormSettings<T> extends SortFormSettingsImpl<T> {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String DEFAULT_FIELD_NAME = "sort";
    private static final String CONFIG_OPTIONS = "options";

    public ConfigurableSortFormSettings(final Configuration configuration, final Function<Configuration, SortFormOption> optionCreator) {
        super(fieldName(configuration), options(configuration, optionCreator));
    }

    private static String fieldName(final Configuration configuration) {
        return configuration.getString(CONFIG_FIELD_NAME, DEFAULT_FIELD_NAME);
    }

    private static List<SortFormOption> options(final Configuration configuration, final Function<Configuration, SortFormOption> optionCreator) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(optionCreator)
                .collect(toList());
    }
}
