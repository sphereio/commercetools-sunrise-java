package com.commercetools.sunrise.search.pagination;

import play.Configuration;

public class ConfigurablePaginationSettings extends PaginationSettingsImpl {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String DEFAULT_FIELD_NAME = "page";
    private static final String CONFIG_DISPLAYED_PAGES = "displayedPages";
    private static final int DEFAULT_DISPLAYED_PAGES = 6;

    public ConfigurablePaginationSettings(final Configuration configuration) {
        super(fieldName(configuration), displayedPages(configuration));
    }

    private static String fieldName(final Configuration configuration) {
        return configuration.getString(CONFIG_FIELD_NAME, DEFAULT_FIELD_NAME);
    }

    private static Integer displayedPages(final Configuration configuration) {
        return configuration.getInt(CONFIG_DISPLAYED_PAGES, DEFAULT_DISPLAYED_PAGES);
    }
}