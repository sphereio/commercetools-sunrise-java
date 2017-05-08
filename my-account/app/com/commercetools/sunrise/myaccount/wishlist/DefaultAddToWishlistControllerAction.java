package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.google.inject.Inject;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddLineItem;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class DefaultAddToWishlistControllerAction extends AbstractSphereRequestExecutor implements AddToWishlistControllerAction {
    @Inject
    protected DefaultAddToWishlistControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<ShoppingList> apply(final ShoppingList shoppingList, final AddToWishlistFormData addToWishlistFormData) {
        return executeRequest(shoppingList, buildRequest(shoppingList, addToWishlistFormData));
    }

    private ShoppingListUpdateCommand buildRequest(final ShoppingList shoppingList, final AddToWishlistFormData addToWishlistFormData) {
        final AddLineItem addLineItem = AddLineItem.of(addToWishlistFormData.productId()).withVariantId(addToWishlistFormData.variantId());
        return ShoppingListUpdateCommand.of(shoppingList, addLineItem);
    }

    private CompletionStage<ShoppingList> executeRequest(final ShoppingList shoppingList, final ShoppingListUpdateCommand command) {
        if (!command.getUpdateActions().isEmpty()) {
            return getSphereClient().execute(command);
        } else {
            return completedFuture(shoppingList);
        }
    }
}
