package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.AbstractControllerComponentSupplier;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchControllerComponent;

import javax.inject.Inject;

public class ProductOverviewSearchControllerComponentsSupplier extends AbstractControllerComponentSupplier {

    @Inject
    public ProductOverviewSearchControllerComponentsSupplier(final ProductSortSelectorControllerComponent productSortSelectorControllerComponent,
                                                             final ProductPaginationControllerComponent productSearchPaginationControllerComponent,
                                                             final ProductSearchBoxControllerComponent productSearchBoxControllerComponent,
                                                             final FacetedSearchControllerComponent facetedSearchControllerComponent) {
        add(productSortSelectorControllerComponent);
        add(productSearchPaginationControllerComponent);
        add(productSearchBoxControllerComponent);
        add(facetedSearchControllerComponent);
    }
}
