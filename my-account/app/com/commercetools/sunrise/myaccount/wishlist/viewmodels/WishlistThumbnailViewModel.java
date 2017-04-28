package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

public class WishlistThumbnailViewModel extends ViewModel {
    private WishlistProductViewModel product;

    public WishlistProductViewModel getProduct() {
        return product;
    }

    public void setProduct(final WishlistProductViewModel product) {
        this.product = product;
    }
}
