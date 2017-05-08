package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.framework.viewmodels.content.shoppinglists.ShoppingListThumbnailViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<ShoppingListThumbnailViewModel> products;

    private String clearWishlistBtn = "Clear Wishlist";

    public GenericListViewModel<ShoppingListThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<ShoppingListThumbnailViewModel> products) {
        this.products = products;
    }

    public String getClearWishlistBtn() {
        return clearWishlistBtn;
    }

    public void setClearWishlistBtn(final String clearWishlistBtn) {
        this.clearWishlistBtn = clearWishlistBtn;
    }
}
