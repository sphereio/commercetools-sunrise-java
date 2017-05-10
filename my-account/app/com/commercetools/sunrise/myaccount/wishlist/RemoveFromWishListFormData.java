package com.commercetools.sunrise.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.LineItem;

@ImplementedBy(DefaultRemoveFromWishListFormData.class)
public interface RemoveFromWishListFormData {

    String productId();

    Integer variantId();

    boolean equals(LineItem lineItem);
}
