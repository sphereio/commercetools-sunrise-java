package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultMyWishlistReverseRouter.class)
public interface MyWishlistReverseRouter {
    String ADD_TO_WISHLIST = "addToWishlist";

    Call addToWishlist(String languageTag);
}
