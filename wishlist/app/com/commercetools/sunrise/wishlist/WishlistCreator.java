package com.commercetools.sunrise.wishlist;

import com.commercetools.sunrise.wishlist.controllers.DefaultWishlistCreator;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;

@ImplementedBy(DefaultWishlistCreator.class)
public interface WishlistCreator {
    /**
     * Creates a wishlist.
     *
     * @return the created wishlist
     */
    CompletionStage<ShoppingList> get();
}
