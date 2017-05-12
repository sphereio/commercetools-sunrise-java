package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(SimpleWishlistReverseRouterByReflection.class)
public interface SimpleWishlistReverseRouter {
    String ADD_TO_WISHLIST_PROCESS = "addToWishlistProcess";

    String REMOVE_FROM_WISHLIST_PROCESS = "removeFromWishlistProcess";

    String CLEAR_WISHLIST_PROCESS = "clearWishlistProcess";

    String WISHLIST_PAGE_CALL = "wishlistPageCall";

    Call addToWishlistProcess(String languageTag);

    Call removeFromWishlistProcess(String languageTag);

    Call clearWishlistProcess(String languageTag);

    Call wishlistPageCall(String languageTag);
}
