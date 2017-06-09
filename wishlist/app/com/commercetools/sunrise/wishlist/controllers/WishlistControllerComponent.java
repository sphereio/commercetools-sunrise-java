package com.commercetools.sunrise.wishlist.controllers;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.application.PageDataReadyHook;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.framework.viewmodels.content.wishlist.WishlistViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.wishlist.WishlistViewModelFactory;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import com.google.inject.Inject;

@RequestScoped
public class WishlistControllerComponent implements ControllerComponent, PageDataReadyHook {
    private final WishlistInSession wishlistInSession;
    private final WishlistViewModelFactory wishlistViewModelFactory;

    @Inject
    protected WishlistControllerComponent(final WishlistInSession wishlistInSession,
                                          final WishlistViewModelFactory wishlistViewModelFactory) {
        this.wishlistInSession = wishlistInSession;
        this.wishlistViewModelFactory = wishlistViewModelFactory;
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        final WishlistViewModel wishlist = wishlistInSession.findWishlist()
                .orElseGet(() -> wishlistViewModelFactory.create(null));
        pageData.getContent().setWishlist(wishlist);
    }
}
