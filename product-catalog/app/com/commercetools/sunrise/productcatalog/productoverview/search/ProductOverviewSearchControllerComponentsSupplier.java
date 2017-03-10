package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.AbstractControllerComponentSupplier;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchControllerComponent;

import javax.inject.Inject;

public class ProductOverviewSearchControllerComponentsSupplier extends AbstractControllerComponentSupplier {

    @Inject
    public ProductOverviewSearchControllerComponentsSupplier(final ProductSearchSortSelectorControllerComponent productSearchSortSelectorControllerComponent,
                                                             final ProductPaginationControllerComponent productSearchPaginationControllerComponent,
                                                             final ProductSearchBoxControllerComponent productSearchBoxControllerComponent,
                                                             final FacetedSearchControllerComponent facetedSearchControllerComponent) {
        add(productSearchSortSelectorControllerComponent);
        add(productSearchPaginationControllerComponent);
        add(productSearchBoxControllerComponent);
        add(facetedSearchControllerComponent);
    }
}
