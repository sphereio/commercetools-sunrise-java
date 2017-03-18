package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractSimpleFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;
import java.util.List;

class SimpleBucketRangeFacetedSearchFormSettingsImpl<T> extends AbstractSimpleFacetedSearchFormSettingsWithOptions<T> implements SimpleBucketRangeFacetedSearchFormSettings<T> {

    private final String fieldName;
    private final List<BucketRangeFacetedSearchFormOption> options;

    SimpleBucketRangeFacetedSearchFormSettingsImpl(final String fieldName, final String label, final String expression,
                                                   final boolean isCountDisplayed, @Nullable final String uiType,
                                                   final boolean isMultiSelect, final boolean isMatchingAll,
                                                   final List<BucketRangeFacetedSearchFormOption> options) {
        super(label, expression, isCountDisplayed, uiType, isMultiSelect, isMatchingAll);
        this.fieldName = fieldName;
        this.options = options;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return options;
    }
}
