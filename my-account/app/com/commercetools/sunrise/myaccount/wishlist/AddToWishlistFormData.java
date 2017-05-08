package com.commercetools.sunrise.myaccount.wishlist;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultAddToWishlistFormData.class)
public interface AddToWishlistFormData {

    String productId();

    Integer variantId();
}
