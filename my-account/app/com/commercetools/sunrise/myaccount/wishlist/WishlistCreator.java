package com.commercetools.sunrise.myaccount.wishlist;

import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;

@ImplementedBy(WishlistCreatorBySession.class)
public interface WishlistCreator {
    /**
     * Creates a wishlist.
     *
     * @return the created wishlist
     */
    CompletionStage<ShoppingList> get();
}
