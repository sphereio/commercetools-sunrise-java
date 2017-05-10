package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistLineItemFormData;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

@ImplementedBy(DefaultAddToWishlistControllerAction.class)
public interface AddToWishlistControllerAction extends ControllerAction, BiFunction<ShoppingList, WishlistLineItemFormData, CompletionStage<ShoppingList>> {
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList shoppingList, WishlistLineItemFormData addToWishlistFormData);
}
