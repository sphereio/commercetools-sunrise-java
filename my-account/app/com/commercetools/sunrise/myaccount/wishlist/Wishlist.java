package com.commercetools.sunrise.myaccount.wishlist;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * A wishlist is combination of a {@link ShoppingList} and
 * the resolved {@link ProductProjection}s of its {@link ShoppingList#getLineItems()}.
 */
public final class Wishlist {
    private final ShoppingList shoppingList;
    private final PagedQueryResult<ProductProjection> products;

    public Wishlist(final PagedQueryResult<ProductProjection> products) {
        this.shoppingList = null;
        this.products = products;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public PagedQueryResult<ProductProjection> getProducts() {
        return products;
    }
}
