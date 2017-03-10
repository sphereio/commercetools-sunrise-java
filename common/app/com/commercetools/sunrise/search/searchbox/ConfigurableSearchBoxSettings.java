package com.commercetools.sunrise.search.searchbox;

import play.Configuration;

public abstract class ConfigurableSearchBoxSettings extends SearchBoxSettingsImpl {

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "q";

    protected ConfigurableSearchBoxSettings(final Configuration configuration) {
        super(configuration.getString(CONFIG_KEY, DEFAULT_KEY));
    }
}