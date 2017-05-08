package com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist;

import com.commercetools.sunrise.framework.reverserouters.AbstractLinksControllerComponent;
import com.commercetools.sunrise.framework.viewmodels.meta.PageMeta;
import com.google.inject.Inject;

public class WishlistLinksControllerComponent  extends AbstractLinksControllerComponent<MyWishlistReverseRouter> {

    private final MyWishlistReverseRouter myWishlistReverseRouter;

    @Inject
    public WishlistLinksControllerComponent(final MyWishlistReverseRouter myWishlistReverseRouter) {
        this.myWishlistReverseRouter = myWishlistReverseRouter;
    }

    @Override
    public MyWishlistReverseRouter getReverseRouter() {
        return myWishlistReverseRouter;
    }

    @Override
    protected void addLinksToPage(final PageMeta meta, final MyWishlistReverseRouter reverseRouter) {
        meta.addHalLink(reverseRouter.addToWishlistProcess("en"), "addToWishlist");
        meta.addHalLink(reverseRouter.myWishlistPageCall("en"), "myWishlist");
    }
}
