package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultMyWishlistReverseRouter.class)
public interface MyWishlistReverseRouter {
    String ADD_TO_WISHLIST_PROCESS = "addToWishlistProcess";
    String MY_WISHLIST_PAGE_CALL = "myWishlistPageCall";

    Call myWishlistPageCall(String languageTag);

    Call addToWishlistProcess(String languageTag);
}
