package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;

public abstract class AbstractFacetedSearchFormSettingsWithOptions<T> extends AbstractFacetedSearchFormSettings<T> implements FacetedSearchFormSettingsWithOptions<T> {

    private final boolean isMultiSelect;
    private final boolean isMatchingAll;

    protected AbstractFacetedSearchFormSettingsWithOptions(final String fieldName, final String label, final String attributePath,
                                                           final boolean isCountDisplayed, @Nullable final String uiType,
                                                           final boolean isMultiSelect, final boolean isMatchingAll) {
        super(fieldName, label, attributePath, isCountDisplayed, uiType);
        this.isMultiSelect = isMultiSelect;
        this.isMatchingAll = isMatchingAll;
    }

    @Override
    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    @Override
    public boolean isMatchingAll() {
        return isMatchingAll;
    }
}
