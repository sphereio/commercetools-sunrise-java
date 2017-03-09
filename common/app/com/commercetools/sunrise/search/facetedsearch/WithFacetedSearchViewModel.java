package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.old.FacetSelectorListViewModel;

public interface WithFacetedSearchViewModel {

    FacetSelectorListViewModel getFacets();

    void setFacets(final FacetSelectorListViewModel facets);
}
