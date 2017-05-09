package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.sessions.customer.CustomerInSession;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftBuilder;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class WishlistCreatorBySession extends AbstractSphereRequestExecutor implements WishlistCreator {
    private final CustomerInSession customerInSession;

    @Inject
    protected WishlistCreatorBySession(final SphereClient sphereClient, final HookRunner hookRunner, final CustomerInSession customerInSession) {
        super(sphereClient, hookRunner);
        this.customerInSession = customerInSession;
    }

    public CompletionStage<ShoppingList> get() {
        final Reference<Customer> customer = customerInSession.findCustomerId()
                .map(Customer::referenceOfId).orElse(null);
        final ShoppingListDraftDsl wishlist = ShoppingListDraftBuilder.of(LocalizedString.ofEnglish("Wishlist"))
                .customer(customer)
                .build();
        final ShoppingListCreateCommand createCommand = ShoppingListCreateCommand.of(wishlist).withExpansionPaths(m -> m.lineItems().variant());

        return getSphereClient().execute(createCommand);
    }
}
