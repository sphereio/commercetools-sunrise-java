package com.commercetools.sunrise.search.facetedsearch;

import play.Configuration;

public abstract class AbstractConfigurableFacetedSearchFormSettingsWithOptions<T> extends AbstractConfigurableFacetedSearchFormSettings<T> implements SimpleFacetedSearchFormSettingsWithOptions<T> {

    private static final String CONFIG_MULTI_SELECT = "multiSelect";
    private static final String CONFIG_MATCHING_ALL = "matchingAll";

    private final boolean multiSelect;
    private final boolean matchingAll;

    protected AbstractConfigurableFacetedSearchFormSettingsWithOptions(final Configuration configuration) {
        super(configuration);
        this.multiSelect = isMultiSelect(configuration);
        this.matchingAll = isMatchingAll(configuration);
    }

    @Override
    public boolean isMultiSelect() {
        return multiSelect;
    }

    @Override
    public boolean isMatchingAll() {
        return matchingAll;
    }

    private static Boolean isMultiSelect(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_MULTI_SELECT, true);
    }

    private static Boolean isMatchingAll(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_MATCHING_ALL, false);
    }
}
