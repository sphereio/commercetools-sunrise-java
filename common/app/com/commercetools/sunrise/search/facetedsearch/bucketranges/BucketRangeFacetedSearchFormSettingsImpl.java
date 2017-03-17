package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;
import java.util.List;

class BucketRangeFacetedSearchFormSettingsImpl<T> extends AbstractFacetedSearchFormSettingsWithOptions<T> implements BucketRangeFacetedSearchFormSettings<T> {

    private final List<BucketRangeFacetedSearchFormOption> options;

    BucketRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                             final boolean isCountDisplayed, @Nullable final String uiType,
                                             final boolean isMultiSelect, final boolean isMatchingAll,
                                             final List<BucketRangeFacetedSearchFormOption> options) {
        super(fieldName, label, expression, isCountDisplayed, uiType, isMultiSelect, isMatchingAll);
        this.options = options;
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }
}
