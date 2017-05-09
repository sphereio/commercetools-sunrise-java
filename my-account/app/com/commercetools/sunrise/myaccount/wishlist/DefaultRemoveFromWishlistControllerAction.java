package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.RemoveLineItem;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class DefaultRemoveFromWishlistControllerAction  extends AbstractSphereRequestExecutor implements RemoveFromWishlistControllerAction {
    @Inject
    protected DefaultRemoveFromWishlistControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<ShoppingList> apply(final ShoppingList shoppingList, final RemoveFromWishListFormData formData) {
        return executeRequest(shoppingList, buildRequest(shoppingList, formData));
    }

    private ShoppingListUpdateCommand buildRequest(final ShoppingList shoppingList, final RemoveFromWishListFormData formData) {
        final RemoveLineItem removeLineItem = RemoveLineItem.of(formData.lineItemId());
        return ShoppingListUpdateCommand.of(shoppingList, removeLineItem);
    }

    private CompletionStage<ShoppingList> executeRequest(final ShoppingList shoppingList, final ShoppingListUpdateCommand command) {
        if (!command.getUpdateActions().isEmpty()) {
            return getSphereClient().execute(command);
        } else {
            return completedFuture(shoppingList);
        }
    }
}
