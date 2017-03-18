package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;

public abstract class AbstractSimpleFacetedSearchFormSettingsWithOptions<T> extends AbstractSimpleFacetedSearchFormSettings<T> implements SimpleFacetedSearchFormSettingsWithOptions<T> {

    private final boolean isMultiSelect;
    private final boolean isMatchingAll;

    protected AbstractSimpleFacetedSearchFormSettingsWithOptions(final String label, final String attributePath,
                                                                 final boolean isCountDisplayed, @Nullable final String uiType,
                                                                 final boolean isMultiSelect, final boolean isMatchingAll) {
        super(label, attributePath, isCountDisplayed, uiType);
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
