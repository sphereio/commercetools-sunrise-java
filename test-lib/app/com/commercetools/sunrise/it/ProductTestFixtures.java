package com.commercetools.sunrise.it;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.producttypes.ProductType;

import java.util.function.Function;

import static com.commercetools.sunrise.it.TestFixtures.*;

public final class ProductTestFixtures {

    private ProductTestFixtures() {
    }

    public static void withProduct(final BlockingSphereClient client, final ProductDraft productDraft, final Function<Product, Product> test) {
        final Product product = client.executeBlocking(ProductCreateCommand.of(productDraft));
        final Product productAfterTest = test.apply(product);
        deleteProductWithRetry(client, productAfterTest);
    }

    public static ProductDraftBuilder productDraft(final ResourceIdentifiable<ProductType> productType, final ProductVariantDraft productVariantDraft) {
        return ProductDraftBuilder.of(productType, randomLocalizedKey(), randomLocalizedKey(), productVariantDraft);
    }

    public static ProductVariantDraftDsl productVariantDraft() {
        return ProductVariantDraftBuilder.of().build();
    }

    public static void deleteProductWithRetry(final BlockingSphereClient client, final Product productAfterTest) {
        deleteWithRetry(client, productAfterTest, ProductDeleteCommand::of, DEFAULT_DELETE_TTL);
    }
}
