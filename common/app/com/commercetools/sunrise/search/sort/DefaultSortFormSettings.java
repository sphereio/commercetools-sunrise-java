package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.SunriseModel;

import java.util.List;

class DefaultSortFormSettings<T> extends SunriseModel implements SortFormSettings<T> {

    private final SimpleSortFormSettings settings;

    DefaultSortFormSettings(final SimpleSortFormSettings settings) {
        this.settings = settings;
    }

    @Override
    public List<SortFormOption> getOptions() {
        return settings.getOptions();
    }

    @Override
    public String getFieldName() {
        return settings.getFieldName();
    }
}
