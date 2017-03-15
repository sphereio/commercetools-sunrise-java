package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;

abstract class MultiOptionFacetedSearchFormSettingsImpl<T> extends FacetedSearchFormSettingsImpl<T> implements MultiOptionFacetedSearchFormSettings<T> {

    private final boolean isMultiSelect;
    private final boolean isMatchingAll;

    MultiOptionFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String attributePath,
                                             final int position, final boolean isCountDisplayed, @Nullable final String uiType,
                                             final boolean isMultiSelect, final boolean isMatchingAll) {
        super(fieldName, label, attributePath, position, isCountDisplayed, uiType);
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
