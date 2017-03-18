package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.SimpleFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;
import java.util.List;

public interface SimpleBucketRangeFacetedSearchFormSettings<T> extends SimpleFacetedSearchFormSettingsWithOptions<T>, FormSettingsWithOptions<BucketRangeFacetedSearchFormOption, String> {

    static <T> SimpleBucketRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String attributePath,
                                                                final boolean isCountDisplayed, @Nullable final String uiType,
                                                                final boolean isMultiSelect, final boolean isMatchingAll,
                                                                final List<BucketRangeFacetedSearchFormOption> options) {
        return new SimpleBucketRangeFacetedSearchFormSettingsImpl<>(fieldName, label, attributePath, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, options);
    }
}
