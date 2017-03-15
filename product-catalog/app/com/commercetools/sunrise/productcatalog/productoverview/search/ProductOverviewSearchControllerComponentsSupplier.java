package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.AbstractControllerComponentSupplier;

import javax.inject.Inject;

public class ProductOverviewSearchControllerComponentsSupplier extends AbstractControllerComponentSupplier {

    @Inject
    public ProductOverviewSearchControllerComponentsSupplier(final ProductSearchSortSelectorControllerComponent productSearchSortSelectorControllerComponent,
                                                             final ProductPaginationControllerComponent productSearchPaginationControllerComponent,
                                                             final ProductSearchBoxControllerComponent productSearchBoxControllerComponent,
                                                             final ProductFacetedSearchSelectorControllerComponent productFacetedSearchSelectorControllerComponent) {
        add(productSearchSortSelectorControllerComponent);
        add(productSearchPaginationControllerComponent);
        add(productSearchBoxControllerComponent);
        add(productFacetedSearchSelectorControllerComponent);
    }
}
