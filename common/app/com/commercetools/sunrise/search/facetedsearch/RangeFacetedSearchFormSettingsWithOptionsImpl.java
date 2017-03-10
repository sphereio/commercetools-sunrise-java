package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

class RangeFacetedSearchFormSettingsWithOptionsImpl extends FacetedSearchFormSettingsWithOptionsImpl<RangeFacetedSearchFormOption> implements RangeFacetedSearchFormSettingsWithOptions {

    RangeFacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<RangeFacetedSearchFormOption> options) {
        super(settings, options);
    }
}
