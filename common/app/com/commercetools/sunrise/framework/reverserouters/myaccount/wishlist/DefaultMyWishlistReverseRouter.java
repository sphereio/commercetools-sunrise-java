package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.commercetools.sunrise.framework.reverserouters.AbstractReflectionReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.ParsedRoutes;
import com.commercetools.sunrise.framework.reverserouters.ReverseCaller;
import com.google.inject.Inject;
import play.mvc.Call;

public class DefaultMyWishlistReverseRouter extends AbstractReflectionReverseRouter implements MyWishlistReverseRouter {

    private final ReverseCaller addToWishlistCaller;
    private final ReverseCaller removeFromWishlistCaller;
    private final ReverseCaller clearWishlistCaller;
    private final ReverseCaller wishlistPageCaller;

    @Inject
    public DefaultMyWishlistReverseRouter(final ParsedRoutes parsedRoutes) {
        addToWishlistCaller = getReverseCallerForSunriseRoute(ADD_TO_WISHLIST_PROCESS, parsedRoutes);
        removeFromWishlistCaller = getReverseCallerForSunriseRoute(REMOVE_FROM_WISHLIST_PROCESS, parsedRoutes);
        clearWishlistCaller = getReverseCallerForSunriseRoute(CLEAR_WISHLIST_PROCESS, parsedRoutes);
        wishlistPageCaller = getReverseCallerForSunriseRoute(MY_WISHLIST_PAGE_CALL, parsedRoutes);
    }

    @Override
    public Call addToWishlistProcess(final String languageTag) {
        return addToWishlistCaller.call(languageTag);
    }

    @Override
    public Call removeFromWishlistProcess(final String languageTag) {
        return removeFromWishlistCaller.call(languageTag);
    }

    @Override
    public Call clearWishlistProcess(final String languageTag) {
        return clearWishlistCaller.call(languageTag);
    }

    @Override
    public Call myWishlistPageCall(final String languageTag) {
        return wishlistPageCaller.call(languageTag);
    }
}
