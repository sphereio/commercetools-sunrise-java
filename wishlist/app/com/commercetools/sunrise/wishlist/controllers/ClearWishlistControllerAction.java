package com.commercetools.sunrise.wishlist.controllers;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultClearWishlistControllerAction.class)
public interface ClearWishlistControllerAction extends ControllerAction, Function<ShoppingList, CompletionStage<ShoppingList>> {
    /**
     * Removes all line items from the given wishlist.
     *
     * @param wishlist the wishlist
     *
     * @return the completion stage for this action
     */
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList wishlist);
}
