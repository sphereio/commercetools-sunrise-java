package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultMyWishlistReverseRouter.class)
public interface MyWishlistReverseRouter {
    String ADD_TO_WISHLIST_PROCESS = "addToWishlistProcess";

    String REMOVE_FROM_WISHLIST_PROCESS = "removeFromWishlistProcess";

    String CLEAR_WISHLIST_PROCESS = "clearWishlistProcess";

    String MY_WISHLIST_PAGE_CALL = "myWishlistPageCall";

    Call addToWishlistProcess(String languageTag);

    Call removeFromWishlistProcess(String languageTag);

    Call clearWishlistProcess(String languageTag);

    Call myWishlistPageCall(String languageTag);
}
