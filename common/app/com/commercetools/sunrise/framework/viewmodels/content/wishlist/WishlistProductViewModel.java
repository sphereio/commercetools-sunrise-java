package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductVariantViewModel;

public class WishlistProductViewModel extends ViewModel {
    private ProductVariantViewModel variant;

    public ProductVariantViewModel getVariant() {
        return variant;
    }

    public void setVariant(final ProductVariantViewModel variant) {
        this.variant = variant;
    }
}
