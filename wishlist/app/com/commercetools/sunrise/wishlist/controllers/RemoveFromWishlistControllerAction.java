package com.commercetools.sunrise.wishlist.controllers;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.commercetools.sunrise.wishlist.viewmodels.RemoveWishlistLineItemFormData;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

@ImplementedBy(DefaultRemoveFromWishlistControllerAction.class)
public interface RemoveFromWishlistControllerAction extends ControllerAction, BiFunction<ShoppingList, RemoveWishlistLineItemFormData, CompletionStage<ShoppingList>> {
    /**
     * Removes a line item from the given wishlist.
     *
     * @param wishlist               the wishlist
     * @param removeWishlistFormData specifies the line item data
     *
     * @return the completion stage for this action
     */
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList wishlist, RemoveWishlistLineItemFormData removeWishlistFormData);
}
