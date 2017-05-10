package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.productcatalog.productoverview.viewmodels.ProductThumbnailViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<ProductThumbnailViewModel> products;
    private Integer itemsInTotal;

    private String clearWishlistBtn = "Clear Wishlist";

    public GenericListViewModel<ProductThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<ProductThumbnailViewModel> products) {
        this.products = products;
    }

    public String getClearWishlistBtn() {
        return clearWishlistBtn;
    }

    public void setClearWishlistBtn(final String clearWishlistBtn) {
        this.clearWishlistBtn = clearWishlistBtn;
    }

    public Integer getItemsInTotal() {
        return itemsInTotal;
    }

    public void setItemsInTotal(final Integer itemsInTotal) {
        this.itemsInTotal = itemsInTotal;
    }
}
