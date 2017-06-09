package com.commercetools.sunrise.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.content.products.ProductWithVariant;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.shoppinglists.LineItem;

public final class WishlistItem extends Base {
    private final LineItem lineItem;
    private final ProductWithVariant productWithVariant;

    private WishlistItem(final LineItem lineItem, final ProductWithVariant productWithVariant) {
        this.lineItem = lineItem;
        this.productWithVariant = productWithVariant;
    }

    public LineItem getLineItem() {
        return lineItem;
    }

    public ProductWithVariant getProductWithVariant() {
        return productWithVariant;
    }

    public static WishlistItem of(final LineItem lineItem, final ProductWithVariant productWithVariant) {
        return new WishlistItem(lineItem, productWithVariant);
    }
}
