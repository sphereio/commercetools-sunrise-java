package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.commercetools.sunrise.framework.reverserouters.AbstractReflectionReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.ParsedRoutes;
import com.commercetools.sunrise.framework.reverserouters.ReverseCaller;
import com.google.inject.Inject;
import play.mvc.Call;

public class DefaultMyWishlistReverseRouter extends AbstractReflectionReverseRouter implements MyWishlistReverseRouter {

    private final ReverseCaller addToWishlistCaller;
    private final ReverseCaller wishlistPageCaller;

    @Inject
    public DefaultMyWishlistReverseRouter(final ParsedRoutes parsedRoutes) {
        addToWishlistCaller = getReverseCallerForSunriseRoute(ADD_TO_WISHLIST, parsedRoutes);
        wishlistPageCaller = getReverseCallerForSunriseRoute(MY_WISHLIST_PAGE_CALL, parsedRoutes);
    }

    @Override
    public Call myWishlistPageCall(final String languageTag) {
        return wishlistPageCaller.call(languageTag);
    }

    @Override
    public Call addToWishlist(final String languageTag) {
        return addToWishlistCaller.call(languageTag);
    }
}
