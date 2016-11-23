package com.commercetools.sunrise.common;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.CartDraftDsl;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerDeleteCommand;
import io.sphere.sdk.customers.commands.CustomerSignInCommand;
import io.sphere.sdk.customers.queries.CustomerByIdGet;
import io.sphere.sdk.models.DefaultCurrencyUnits;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.randomEmail;

public class TestFixtures {

    public static String CUSTOMER_PASSWORD = "PSW-12345";

    public static void withCart(final BlockingSphereClient client, final CartDraft cartDraft, final Function<Cart, Cart> test) {
        final Cart cart = client.executeBlocking(CartCreateCommand.of(cartDraft));
        final Cart cartAfterTest = test.apply(cart);
        deleteCartWithRetry(client, cart.getId(), cartAfterTest);
    }

    public static void withCustomer(final BlockingSphereClient client, final CustomerDraft customerDraft, final Function<CustomerSignInResult, Customer> test) {
        final CustomerSignInResult customerSignInResult = client.executeBlocking(CustomerCreateCommand.of(customerDraft));
        final Customer customerAfterTest = test.apply(customerSignInResult);
        deleteCustomerWithRetry(client, customerSignInResult.getCustomer().getId(), customerAfterTest);
    }

    public static CustomerSignInResult logInCustomer(final BlockingSphereClient client, final Customer customer, @Nullable final Cart cart) {
        final String cartId = cart != null ? cart.getId() : null;
        return client.executeBlocking(CustomerSignInCommand.of(customer.getEmail(), CUSTOMER_PASSWORD, cartId));
    }

    public static CartDraftDsl cartDraft() {
        return CartDraft.of(DefaultCurrencyUnits.EUR);
    }

    public static CustomerDraftDsl customerDraft() {
        return CustomerDraftBuilder.of(randomEmail(TestFixtures.class), TestFixtures.CUSTOMER_PASSWORD).build();
    }

    private static void deleteCartWithRetry(final BlockingSphereClient client, final String cartId, final Cart cartAfterTest) {
        deleteWithRetry(client, cartAfterTest, () -> CartByIdGet.of(cartId), CartDeleteCommand::of);
    }

    private static void deleteCustomerWithRetry(final BlockingSphereClient client, final String customerId, final Customer customerAfterTest) {
        deleteWithRetry(client, customerAfterTest, () -> CustomerByIdGet.of(customerId), CustomerDeleteCommand::of);
    }

    private static <T> void deleteWithRetry(final BlockingSphereClient client, final T resource,
                                            final Supplier<SphereRequest<T>> fetchSupplier,
                                            final Function<T, SphereRequest<T>> deleteFunction) {
        try {
            client.executeBlocking(deleteFunction.apply(resource));
        } catch (ConcurrentModificationException e) {
            final T resourceWithCorrectVersion = client.executeBlocking(fetchSupplier.get());
            if (resourceWithCorrectVersion != null) {
                client.executeBlocking(deleteFunction.apply(resourceWithCorrectVersion));
            }
        } catch (NotFoundException e) {
            // mission indirectly accomplished
        }
    }
}
