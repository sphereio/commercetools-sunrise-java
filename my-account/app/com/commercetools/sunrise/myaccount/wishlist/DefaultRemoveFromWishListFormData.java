package com.commercetools.sunrise.myaccount.wishlist;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.shoppinglists.LineItem;

import java.util.Objects;

public class DefaultRemoveFromWishListFormData extends Base implements RemoveFromWishListFormData {
    private String productId;
    private Integer variantId;

    @Override
    public String productId() {
        return productId;
    }

    @Override
    public Integer variantId() {
        return variantId;
    }

    @Override
    public boolean equals(final LineItem lineItem) {
        return Objects.equals(lineItem.getProductId(), productId) && Objects.equals(lineItem.getVariantId(), variantId);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(final Integer variantId) {
        this.variantId = variantId;
    }
}
