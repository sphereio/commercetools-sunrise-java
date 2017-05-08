package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultClearWishlistControllerAction.class)
public interface ClearWishlistControllerAction extends ControllerAction, Function<ShoppingList, CompletionStage<ShoppingList>> {
    @Override
    CompletionStage<ShoppingList> apply(ShoppingList shoppingList);
}
