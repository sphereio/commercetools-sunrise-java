package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.framework.viewmodels.forms.WithFormFieldName;
import com.commercetools.sunrise.framework.viewmodels.forms.WithFormOptions;
import com.commercetools.sunrise.search.facetedsearch.SimpleFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;
import java.util.List;

public interface SimpleBucketRangeFacetedSearchFormSettings extends SimpleFacetedSearchFormSettingsWithOptions, WithFormFieldName, WithFormOptions<BucketRangeFacetedSearchFormOption, String> {

    static <T> SimpleBucketRangeFacetedSearchFormSettings of(final String fieldName, final String label, final String attributePath,
                                                                final boolean isCountDisplayed, @Nullable final String uiType,
                                                                final boolean isMultiSelect, final boolean isMatchingAll,
                                                                final List<BucketRangeFacetedSearchFormOption> options) {
        return new SimpleBucketRangeFacetedSearchFormSettingsImpl(fieldName, label, attributePath, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, options);
    }
}
