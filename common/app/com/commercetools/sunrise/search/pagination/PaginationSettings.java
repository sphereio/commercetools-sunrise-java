package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettings;
import play.Configuration;

public class PaginationSettings extends AbstractFormSettings<Integer> {

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "page";
    private static final String CONFIG_DISPLAYED_PAGES = "displayedPages";
    private static final int DEFAULT_DISPLAYED_PAGES = 6;
    private static final int DEFAULT_PAGE = 1;

    private final int displayedPages;

    protected PaginationSettings(final Configuration configuration) {
        super(key(configuration), DEFAULT_PAGE);
        this.displayedPages = configuration.getInt(CONFIG_DISPLAYED_PAGES, DEFAULT_DISPLAYED_PAGES);
    }

    public int getDisplayedPages() {
        return displayedPages;
    }

    @Override
    public Integer mapToValue(final String valueAsString) {
        try {
            return Integer.valueOf(valueAsString);
        } catch (NumberFormatException e) {
            return getDefaultValue();
        }
    }

    @Override
    public boolean isValidValue(final Integer value) {
        return value != null && value > 0;
    }

    public static PaginationSettings of(final Configuration configuration) {
        return new PaginationSettings(configuration);
    }

    private static String key(final Configuration configuration) {
        return configuration.getString(CONFIG_KEY, DEFAULT_KEY);
    }
}