package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

public class FacetSelectorViewModel extends ViewModel {

    private FacetViewModel facet;

    public FacetSelectorViewModel() {
    }

    public FacetViewModel getFacet() {
        return facet;
    }

    public void setFacet(final FacetViewModel facet) {
        this.facet = facet;
    }
}
