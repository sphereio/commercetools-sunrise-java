package com.commercetools.sunrise.search.pagination;

import play.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public abstract class ConfigurableEntriesPerPageFormSettings extends EntriesPerPageFormSettingsImpl {

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "pp";
    private static final String CONFIG_OPTIONS = "options";

    protected ConfigurableEntriesPerPageFormSettings(final Configuration configuration) {
        super(key(configuration), options(configuration));
    }

    private static String key(final Configuration configuration) {
        return configuration.getString(CONFIG_KEY, DEFAULT_KEY);
    }

    private static List<EntriesPerPageFormOption> options(final Configuration configuration) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(ConfigurableEntriesPerPageFormOption::new)
                .collect(toList());
    }
}
