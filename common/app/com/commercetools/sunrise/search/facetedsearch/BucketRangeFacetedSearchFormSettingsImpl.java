package com.commercetools.sunrise.search.facetedsearch;

import javax.annotation.Nullable;
import java.util.List;

class BucketRangeFacetedSearchFormSettingsImpl<T> extends MultiOptionFacetedSearchFormSettingsImpl<T> implements BucketRangeFacetedSearchFormSettings<T> {

    private final List<BucketRangeFacetedSearchFormOption> options;

    BucketRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression, final int position,
                                             final boolean isCountDisplayed, @Nullable final String uiType,
                                             final boolean isMultiSelect, final boolean isMatchingAll,
                                             final List<BucketRangeFacetedSearchFormOption> options) {
        super(fieldName, label, expression, position, isCountDisplayed, uiType, isMultiSelect, isMatchingAll);
        this.options = options;
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }
}
