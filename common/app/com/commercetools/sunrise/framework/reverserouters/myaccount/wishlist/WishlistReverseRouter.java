package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.commercetools.sunrise.framework.reverserouters.LocalizedReverseRouter;
import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultWishlistReverseRouter.class)
public interface WishlistReverseRouter extends SimpleWishlistReverseRouter, LocalizedReverseRouter {

    default Call addToWishlistProcess() {
        return addToWishlistProcess(locale().toLanguageTag());
    }

    default Call removeFromWishlistProcess() {
        return removeFromWishlistProcess(locale().toLanguageTag());
    }

    default Call clearWishlistProcess() {
        return clearWishlistProcess(locale().toLanguageTag());
    }

    default Call wishlistPageCall() {
        return wishlistPageCall(locale().toLanguageTag());
    }
}
