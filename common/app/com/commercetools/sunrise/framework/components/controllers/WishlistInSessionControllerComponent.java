package com.commercetools.sunrise.framework.components.controllers;

import com.commercetools.sunrise.framework.hooks.application.PageDataReadyHook;
import com.commercetools.sunrise.framework.hooks.ctpevents.ShoppingListCreatedHook;
import com.commercetools.sunrise.framework.hooks.ctpevents.ShoppingListLoadedHook;
import com.commercetools.sunrise.framework.hooks.ctpevents.ShoppingListUpdatedHook;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.framework.viewmodels.content.wishlist.WishlistViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.wishlist.WishlistViewModelFactory;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

@RequestScoped
public class WishlistInSessionControllerComponent implements ControllerComponent,
        ShoppingListCreatedHook, ShoppingListUpdatedHook, ShoppingListLoadedHook,
        PageDataReadyHook {
    private final WishlistInSession wishlistInSession;
    private final WishlistViewModelFactory wishlistViewModelFactory;

    @Inject
    protected WishlistInSessionControllerComponent(final WishlistInSession wishlistInSession,
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

    @Override
    public CompletionStage<?> onShoppingListLoaded(final ShoppingList shoppingList) {
        overwriteWishlistInSession(shoppingList);
        return completedFuture(null);
    }

    @Override
    public CompletionStage<?> onShoppingListCreated(final ShoppingList shoppingList) {
        overwriteWishlistInSession(shoppingList);
        return completedFuture(null);
    }

    @Override
    public CompletionStage<?> onShoppingListUpdated(final ShoppingList shoppingList) {
        overwriteWishlistInSession(shoppingList);
        return completedFuture(null);
    }

    private void overwriteWishlistInSession(@Nullable final ShoppingList wishlist) {
        wishlistInSession.store(wishlist);
    }
}
