package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormOption;

abstract class FacetedSearchFormOptionImpl extends AbstractFormOption<String> implements FacetedSearchFormOption {

    private final long count;

    FacetedSearchFormOptionImpl(final String fieldLabel, final String fieldValue, final String value, final long count) {
        super(fieldLabel, fieldValue, value, false);
        this.count = count;
    }

    @Override
    public long getCount() {
        return count;
    }
}
