package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.google.inject.Inject;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.queries.ShoppingListByIdGet;

import java.util.concurrent.CompletionStage;

public class WishlistFinder extends AbstractSphereRequestExecutor {
    @Inject
    protected WishlistFinder(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    public CompletionStage<ShoppingList> findById(final String id) {
        final ShoppingListByIdGet get = ShoppingListByIdGet.of(id);
        return getSphereClient().execute(get);
    }
}
