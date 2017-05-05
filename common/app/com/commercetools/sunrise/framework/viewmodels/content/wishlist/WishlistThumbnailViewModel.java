package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

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
