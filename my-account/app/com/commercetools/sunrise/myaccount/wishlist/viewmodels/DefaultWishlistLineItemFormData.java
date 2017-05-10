package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.shoppinglists.LineItem;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

import java.util.Objects;

public class DefaultWishlistLineItemFormData extends Base implements WishlistLineItemFormData {

    @Required
    @MinLength(1)
    private String productId;
    @Required
    @Min(1)
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

    // Getters & setters

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
