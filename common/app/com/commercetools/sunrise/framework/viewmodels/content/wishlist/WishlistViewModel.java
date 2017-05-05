package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.ViewModel;

/**
 * This view model is responsible for storing the wishlist.
 */
public class WishlistViewModel extends ViewModel {
    private String name;

    private GenericListViewModel<WishlistThumbnailViewModel> products;

    public GenericListViewModel<WishlistThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<WishlistThumbnailViewModel> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
