package com.commercetools.sunrise.search.pagination;

import play.Configuration;

public abstract class ConfigurablePaginationSettings extends PaginationSettingsImpl {

    private static final String CONFIG_KEY = "fieldName";
    private static final String DEFAULT_KEY = "page";
    private static final String CONFIG_DISPLAYED_PAGES = "displayedPages";
    private static final int DEFAULT_DISPLAYED_PAGES = 6;

    protected ConfigurablePaginationSettings(final Configuration configuration) {
        super(key(configuration), displayedPages(configuration));
    }

    private static String key(final Configuration configuration) {
        return configuration.getString(CONFIG_KEY, DEFAULT_KEY);
    }

    private static Integer displayedPages(final Configuration configuration) {
        return configuration.getInt(CONFIG_DISPLAYED_PAGES, DEFAULT_DISPLAYED_PAGES);
    }
}