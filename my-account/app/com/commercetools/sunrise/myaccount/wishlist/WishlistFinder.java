package com.commercetools.sunrise.myaccount.wishlist;

import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;

public interface WishlistFinder {

    /**
     * If the current session contains a signed in customer or a previously created wishlist, this wishlist
     * will be returned. Otherwise a new wishlist will be created and stored in the current session.
     *
     * @return the wishlist
     */
    CompletionStage<ShoppingList> getOrCreate();

    CompletionStage<Wishlist> getWishList(ShoppingList shoppingList);
}
