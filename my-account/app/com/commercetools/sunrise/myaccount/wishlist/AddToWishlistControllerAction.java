package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

@ImplementedBy(DefaultAddToWishlistControllerAction.class)
public interface AddToWishlistControllerAction extends ControllerAction, BiFunction<ShoppingList, AddToWishlistFormData, CompletionStage<ShoppingList>> {
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList shoppingList, AddToWishlistFormData addToWishlistFormData);
}
