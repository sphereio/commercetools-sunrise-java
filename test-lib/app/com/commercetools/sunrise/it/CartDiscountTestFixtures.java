package com.commercetools.sunrise.it;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommand;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountDeleteCommand;
import io.sphere.sdk.client.BlockingSphereClient;

import java.util.function.Function;

import static com.commercetools.sunrise.it.TestFixtures.DEFAULT_DELETE_TTL;
import static com.commercetools.sunrise.it.TestFixtures.deleteWithRetry;

public final class CartDiscountTestFixtures {

    private CartDiscountTestFixtures() {
    }

    public static void withCartDiscount(final BlockingSphereClient client, final CartDiscountDraft cartDiscountDraft, final Function<CartDiscount, CartDiscount> test) {
        final CartDiscount cartDiscount = client.executeBlocking(CartDiscountCreateCommand.of(cartDiscountDraft));
        final CartDiscount cartDiscountAfterTest = test.apply(cartDiscount);
        deleteCartDiscountWithRetry(client, cartDiscountAfterTest);
    }

    public static void deleteCartDiscountWithRetry(final BlockingSphereClient client, final CartDiscount cartDiscount) {
        deleteWithRetry(client, cartDiscount, CartDiscountDeleteCommand::of, DEFAULT_DELETE_TTL);
    }
}
