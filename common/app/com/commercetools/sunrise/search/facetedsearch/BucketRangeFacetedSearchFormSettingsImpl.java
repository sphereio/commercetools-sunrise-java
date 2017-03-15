package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;
import java.util.List;

class BucketRangeFacetedSearchFormSettingsImpl<T> extends FacetedSearchFormSettingsImpl<T> implements BucketRangeFacetedSearchFormSettings<T> {

    private final List<BucketRangeFacetedSearchFormOption> options;

    BucketRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression, final int position,
                                             final boolean isCountDisplayed, final boolean isMultiSelect, final boolean isMatchingAll,
                                             @Nullable final String uiType, final List<BucketRangeFacetedSearchFormOption> options) {
        super(fieldName, label, expression, position, isCountDisplayed, isMultiSelect, isMatchingAll, uiType, null);
        this.options = options;
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }
}
