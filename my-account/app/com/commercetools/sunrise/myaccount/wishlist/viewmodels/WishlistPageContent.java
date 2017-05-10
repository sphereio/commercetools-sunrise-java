package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.productcatalog.productoverview.viewmodels.ProductThumbnailViewModel;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageSelectorViewModel;
import com.commercetools.sunrise.search.pagination.viewmodels.PaginationViewModel;
import com.commercetools.sunrise.search.pagination.viewmodels.WithPaginationViewModel;

/**
 * The page content for the wishlist.
 */
public class WishlistPageContent extends PageContent implements WithPaginationViewModel {
    private GenericListViewModel<ProductThumbnailViewModel> products;
    private Integer itemsInTotal;

    private String clearWishlistBtn = "Clear Wishlist";

    private PaginationViewModel pagination;
    private EntriesPerPageSelectorViewModel displaySelector;

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

    @Override
    public PaginationViewModel getPagination() {
        return pagination;
    }

    @Override
    public void setPagination(final PaginationViewModel pagination) {
        this.pagination = pagination;
    }

    @Override
    public EntriesPerPageSelectorViewModel getDisplaySelector() {
        return displaySelector;
    }

    @Override
    public void setDisplaySelector(final EntriesPerPageSelectorViewModel displaySelector) {
        this.displaySelector = displaySelector;
    }
}
