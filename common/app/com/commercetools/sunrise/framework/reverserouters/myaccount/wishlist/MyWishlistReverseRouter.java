package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultMyWishlistReverseRouter.class)
public interface MyWishlistReverseRouter {
    String ADD_TO_WISHLIST_PROCESS = "addToWishlistProcess";
    String REMOVE_FROM_WISHLIST_PROCESS = "removeFromWishlistProcess";
    String MY_WISHLIST_PAGE_CALL = "myWishlistPageCall";

    Call myWishlistPageCall(String languageTag);

    Call addToWishlistProcess(String languageTag);

    Call removeFromWishlistProcess(String languageTag);
}
