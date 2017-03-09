package com.commercetools.sunrise.search.facetedsearch;

public interface WithFacetedSearchViewModel {

    FacetSelectorListViewModel getFacets();

    void setFacets(final FacetSelectorListViewModel facets);
}
