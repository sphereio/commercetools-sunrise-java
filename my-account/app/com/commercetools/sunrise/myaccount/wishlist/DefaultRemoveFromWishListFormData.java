package com.commercetools.sunrise.myaccount.wishlist;

import io.sphere.sdk.models.Base;

public class DefaultRemoveFromWishListFormData extends Base implements RemoveFromWishListFormData {
    private String lineItemId;

    @Override
    public String lineItemId() {
        return lineItemId;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(final String lineItemId) {
        this.lineItemId = lineItemId;
    }
}
