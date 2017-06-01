package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.AddWishlistLineItemFormData;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddLineItem;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class DefaultAddToWishlistControllerAction extends AbstractShoppingListUpdateExecutor implements AddToWishlistControllerAction {
    @Inject
    protected DefaultAddToWishlistControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<ShoppingList> apply(final ShoppingList shoppingList, final AddWishlistLineItemFormData addToWishlistFormData) {
        return executeRequest(shoppingList, buildRequest(shoppingList, addToWishlistFormData));
    }

    protected ShoppingListUpdateCommand buildRequest(final ShoppingList shoppingList, final AddWishlistLineItemFormData addToWishlistFormData) {
        final AddLineItem addLineItem = AddLineItem.of(addToWishlistFormData.productId()).withVariantId(addToWishlistFormData.variantId());
        return ShoppingListUpdateCommand.of(shoppingList, addLineItem);
    }
}
