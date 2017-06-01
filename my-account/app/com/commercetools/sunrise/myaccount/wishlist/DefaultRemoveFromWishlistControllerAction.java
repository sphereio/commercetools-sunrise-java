package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.RemoveWishlistLineItemFormData;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.RemoveLineItem;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class DefaultRemoveFromWishlistControllerAction extends AbstractShoppingListUpdateExecutor implements RemoveFromWishlistControllerAction {
    @Inject
    protected DefaultRemoveFromWishlistControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<ShoppingList> apply(final ShoppingList shoppingList, final RemoveWishlistLineItemFormData formData) {
        return executeRequest(shoppingList, buildRequest(shoppingList, formData));
    }

    protected ShoppingListUpdateCommand buildRequest(final ShoppingList shoppingList, final RemoveWishlistLineItemFormData formData) {
        final LineItem lineItemToRemove = shoppingList.getLineItems().stream()
                .filter(lineItem -> formData.lineItemId().equals(lineItem.getId()))
                .findFirst()
                .get();

        final RemoveLineItem removeLineItem = RemoveLineItem.of(lineItemToRemove);
        return ShoppingListUpdateCommand.of(shoppingList, removeLineItem);
    }
}
