package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.framework.viewmodels.content.shoppinglists.ShoppingListThumbnailViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent {
    private GenericListViewModel<ShoppingListThumbnailViewModel> products;
    private Integer itemsInTotal;
    private Integer pagination;

    private String clearWishlistBtn = "Clear Wishlist";

    public GenericListViewModel<ShoppingListThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<ShoppingListThumbnailViewModel> products) {
        this.products = products;
        this.itemsInTotal = products.getList().size();
        this.pagination = 1;
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

    public Integer getPagination() {
        return pagination;
    }

    public void setPagination(final Integer pagination) {
        this.pagination = pagination;
    }
}
