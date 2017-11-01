package com.commercetools.sunrise.it;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductVariantDraft;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxCategoryDraftBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import static com.commercetools.sunrise.it.ProductTestFixtures.*;
import static com.commercetools.sunrise.it.ProductTypeTestFixtures.productTypeDraft;
import static com.commercetools.sunrise.it.ProductTypeTestFixtures.withProductType;
import static com.commercetools.sunrise.it.TaxCategoryTestFixtures.withTaxCategory;
import static com.commercetools.sunrise.it.TestFixtures.*;

public final class CartTestFixtures {

    private CartTestFixtures() {
    }

    public static void withCart(final BlockingSphereClient client, final CartDraft cartDraft, final Function<Cart, Cart> test) {
        final Cart cart = client.executeBlocking(CartCreateCommand.of(cartDraft));
        final Cart cartAfterTest = test.apply(cart);
        deleteCartWithRetry(client, cartAfterTest);
    }

    public static void withTaxedAndFilledCart(final BlockingSphereClient client, final Function<Cart, Cart> test) {
        final TaxCategoryDraft taxCategoryDraft =
                TaxCategoryDraftBuilder.of(randomString(), Collections.emptyList(), null)
                        .build();
        withTaxCategory(client, taxCategoryDraft, taxCategory -> {
            withProductType(client, productTypeDraft(), productType -> {
                final ProductVariantDraft productVariantDraft = ProductVariantDraftBuilder.of()
                        .price(PriceDraft.of(BigDecimal.TEN, DefaultCurrencyUnits.EUR))
                        .build();
                final ProductDraft productDraft = productDraft(productType, productVariantDraft())
                        .publish(true)
                        .taxCategory(taxCategory)
                        .masterVariant(productVariantDraft)
                        .build();
                withProduct(client, productDraft, product -> {
                    final LineItemDraft lineItemDraft = LineItemDraft.of(product, 1, 1L);
                    final CartDraft cartDraft = CartDraftBuilder.of(DefaultCurrencyUnits.EUR)
                            .lineItems(Arrays.asList(lineItemDraft))
                            .build();
                    withCart(client, cartDraft, cart -> test.apply(cart));
                    return product;
                });
                return productType;
            });

            return taxCategory;
        });

    }

    public static CartDraftDsl cartDraft() {
        return CartDraft.of(DefaultCurrencyUnits.EUR);
    }

    public static void deleteCartWithRetry(final BlockingSphereClient client, final Cart cartAfterTest) {
        deleteWithRetry(client, cartAfterTest, CartDeleteCommand::of, DEFAULT_DELETE_TTL);
    }
}
