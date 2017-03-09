package com.commercetools.sunrise.search;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchControllerComponent;
import com.commercetools.sunrise.search.pagination.PaginationControllerComponent;
import com.commercetools.sunrise.search.searchbox.SearchBoxControllerComponent;
import com.commercetools.sunrise.search.sort.SortSelectorControllerComponent;
import com.commercetools.sunrise.framework.components.controllers.AbstractControllerComponentSupplier;

import javax.inject.Inject;

public class SearchControllerComponentsSupplier extends AbstractControllerComponentSupplier {

    @Inject
    public SearchControllerComponentsSupplier(final SortSelectorControllerComponent sortSelectorControllerComponent,
                                              final PaginationControllerComponent paginationControllerComponent,
                                              final SearchBoxControllerComponent searchBoxControllerComponent,
                                              final FacetedSearchControllerComponent facetedSearchControllerComponent) {
        add(sortSelectorControllerComponent);
        add(paginationControllerComponent);
        add(searchBoxControllerComponent);
        add(facetedSearchControllerComponent);
    }
}
