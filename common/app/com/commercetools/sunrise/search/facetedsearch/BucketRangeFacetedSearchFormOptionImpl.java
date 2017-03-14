package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormOption;

class BucketRangeFacetedSearchFormOptionImpl extends AbstractFormOption<String> implements BucketRangeFacetedSearchFormOption {

    BucketRangeFacetedSearchFormOptionImpl(final String fieldLabel, final String fieldValue, final String expression) {
        super(fieldLabel, fieldValue, expression, false);
    }
}
