package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

class TermFacetedSearchFormSettingsWithOptionsImpl extends FacetedSearchFormSettingsWithOptionsImpl<TermFacetedSearchFormOption> implements TermFacetedSearchFormSettingsWithOptions {

    TermFacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<TermFacetedSearchFormOption> options) {
        super(settings, options);
    }
}
