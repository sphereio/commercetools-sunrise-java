package com.commercetools.sunrise.myaccount.wishlist;

import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface WishlistFinder {
    /**
     * If the current session contains a signed in customer or a previously created wishlist, this wishlist
     * will be returned. Otherwise an empty optional will be returned.
     *
     * @return the wishlist
     */
    CompletionStage<Optional<ShoppingList>> get();
}
