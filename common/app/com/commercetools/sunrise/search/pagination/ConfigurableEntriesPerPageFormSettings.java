package com.commercetools.sunrise.search.pagination;

import play.Configuration;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ConfigurableEntriesPerPageFormSettings extends EntriesPerPageFormSettingsImpl {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String DEFAULT_FIELD_NAME = "pp";
    private static final String CONFIG_OPTIONS = "options";

    public ConfigurableEntriesPerPageFormSettings(final Configuration configuration, final Function<Configuration, EntriesPerPageFormOption> optionCreator) {
        super(fieldName(configuration), options(configuration, optionCreator));
    }

    private static String fieldName(final Configuration configuration) {
        return configuration.getString(CONFIG_FIELD_NAME, DEFAULT_FIELD_NAME);
    }

    private static List<EntriesPerPageFormOption> options(final Configuration configuration, final Function<Configuration, EntriesPerPageFormOption> optionCreator) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(optionCreator)
                .collect(toList());
    }
}
