package com.commercetools.sunrise.wishlist.content.viewmodels;

import com.commercetools.sunrise.core.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.core.viewmodels.content.PageContent;
import com.commercetools.sunrise.models.products.ProductThumbnailViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<ProductThumbnailViewModel> products;
    private Integer itemsInTotal;

    public GenericListViewModel<ProductThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<ProductThumbnailViewModel> products) {
        this.products = products;
    }

    public Integer getItemsInTotal() {
        return itemsInTotal;
    }

    public void setItemsInTotal(final Integer itemsInTotal) {
        this.itemsInTotal = itemsInTotal;
    }
}
