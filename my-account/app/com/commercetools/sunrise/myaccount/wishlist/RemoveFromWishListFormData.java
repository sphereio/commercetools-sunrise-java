package com.commercetools.sunrise.myaccount.wishlist;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultRemoveFromWishListFormData.class)
public interface RemoveFromWishListFormData {

    String lineItemId();
}
