package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormOption;

public interface FacetedSearchFormOption extends FormOption<String> {

    /**
     * Gets the amount of results present in this facet option.
     * @return the option count
     */
    long getCount();
}
