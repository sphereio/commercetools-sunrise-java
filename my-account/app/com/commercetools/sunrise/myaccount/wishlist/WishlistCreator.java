package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.google.inject.Inject;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftBuilder;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;

import java.util.concurrent.CompletionStage;

public class WishlistCreator extends AbstractSphereRequestExecutor {

    @Inject
    protected WishlistCreator(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    public CompletionStage<ShoppingList> get() {
        final ShoppingListDraftDsl wishlist = ShoppingListDraftBuilder.of(LocalizedString.ofEnglish("Wishlist"))
                .build();
        final ShoppingListCreateCommand createCommand = ShoppingListCreateCommand.of(wishlist);

        return getSphereClient().execute(createCommand);
    }
}
