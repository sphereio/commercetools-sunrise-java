package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.PageContent;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<WishlistThumbnailViewModel> products;

    public GenericListViewModel<WishlistThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<WishlistThumbnailViewModel> products) {
        this.products = products;
    }
}
