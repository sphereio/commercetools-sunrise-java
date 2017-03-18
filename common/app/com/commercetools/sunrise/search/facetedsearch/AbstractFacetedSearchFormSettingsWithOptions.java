package com.commercetools.sunrise.search.facetedsearch;

import java.util.Locale;

public abstract class AbstractFacetedSearchFormSettingsWithOptions<T, S extends SimpleFacetedSearchFormSettingsWithOptions<T>> extends AbstractFacetedSearchFormSettings<T, S> implements SimpleFacetedSearchFormSettingsWithOptions<T> {

    protected AbstractFacetedSearchFormSettingsWithOptions(final S settings, final Locale locale) {
        super(settings, locale);
    }

    public boolean isMultiSelect() {
        return getSettings().isMultiSelect();
    }

    public boolean isMatchingAll() {
        return getSettings().isMatchingAll();
    }
}
