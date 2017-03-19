package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;

import java.util.List;
import java.util.Locale;

public class DefaultBucketRangeFacetedSearchFormSettings<T> extends AbstractFacetedSearchFormSettingsWithOptions<SimpleBucketRangeFacetedSearchFormSettings> implements BucketRangeFacetedSearchFormSettings<T> {

    public DefaultBucketRangeFacetedSearchFormSettings(final SimpleBucketRangeFacetedSearchFormSettings settings, final Locale locale) {
        super(settings, locale);
    }

    @Override
    public String getFieldName() {
        return getSettings().getFieldName();
    }

    @Override
    public List<BucketRangeFacetedSearchFormOption> getOptions() {
        return getSettings().getOptions();
    }
}
