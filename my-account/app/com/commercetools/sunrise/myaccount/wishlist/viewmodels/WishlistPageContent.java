package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.framework.viewmodels.content.wishlist.WishlistThumbnailViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<WishlistThumbnailViewModel> products;

    private String clearWishlistBtn = "Clear Wishlist";

    public GenericListViewModel<WishlistThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<WishlistThumbnailViewModel> products) {
        this.products = products;
    }

    public String getClearWishlistBtn() {
        return clearWishlistBtn;
    }

    public void setClearWishlistBtn(final String clearWishlistBtn) {
        this.clearWishlistBtn = clearWishlistBtn;
    }
}
