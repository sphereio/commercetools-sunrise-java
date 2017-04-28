package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.google.inject.Inject;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.*;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

public class MyWishlistCreator extends AbstractSphereRequestExecutor {

    @Inject
    protected MyWishlistCreator(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    public CompletionStage<ShoppingList> get() {
        final LineItemDraftDsl lineItemDraft = LineItemDraftBuilder.of("bc832d9c-316f-48f9-9872-94e10f9cd2e7")
                .variantId(1)
                .build();
        ShoppingListDraftDsl wishlist = ShoppingListDraftBuilder.of(LocalizedString.ofEnglish("Wishlist"))
                .lineItems(Arrays.asList(lineItemDraft))
                .build();
        ShoppingListCreateCommand createCommand = ShoppingListCreateCommand.of(wishlist)
                .withExpansionPaths(m -> m.lineItems().variant());

        return getSphereClient().execute(createCommand);
    }
}
