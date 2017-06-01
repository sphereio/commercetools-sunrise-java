package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;

/**
 * A wishlist is combination of a {@link ShoppingList} and the currently viewed {@link #getProducts()}
 * of its {@link ShoppingList#getLineItems()}.
 */
public final class Wishlist {
    private final ShoppingList shoppingList;
    private final List<ProductProjection> products;

    public Wishlist(final ShoppingList shoppingList, final List<ProductProjection> products) {
        this.shoppingList = shoppingList;
        this.products = products;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public List<ProductProjection> getProducts() {
        return products;
    }
}
