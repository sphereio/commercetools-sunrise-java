package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.google.inject.Inject;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommand;
import io.sphere.sdk.shoppinglists.commands.updateactions.AddLineItem;
import io.sphere.sdk.shoppinglists.commands.updateactions.RemoveLineItem;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class DefaultClearWishlistControllerAction extends AbstractSphereRequestExecutor implements ClearWishlistControllerAction {
    @Inject
    protected DefaultClearWishlistControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<ShoppingList> apply(final ShoppingList shoppingList) {
        return executeRequest(shoppingList, buildRequest(shoppingList));
    }

    private ShoppingListUpdateCommand buildRequest(final ShoppingList shoppingList) {
        final List<RemoveLineItem> removeLineItems = shoppingList.getLineItems().stream()
                .map(RemoveLineItem::of)
                .collect(Collectors.toList());

        return ShoppingListUpdateCommand.of(shoppingList, removeLineItems);
    }

    private CompletionStage<ShoppingList> executeRequest(final ShoppingList shoppingList, final ShoppingListUpdateCommand command) {
        if (!command.getUpdateActions().isEmpty()) {
            return getSphereClient().execute(command);
        } else {
            return completedFuture(shoppingList);
        }
    }
}
