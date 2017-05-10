package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * The form data used to add and remove line items from the wishlist.
 *
 * @see ShoppingList#getLineItems()
 * @see LineItem#getProductId()
 * @see LineItem#getVariantId()
 */
@ImplementedBy(DefaultWishlistLineItemFormData.class)
public interface WishlistLineItemFormData {

    String productId();

    Integer variantId();

    /**
     * This method allows to compare this object with the given line item.
     *
     * @param lineItem the line item to compare
     *
     * @return true iff. the line item refers to the same product variant
     */
    boolean equals(LineItem lineItem);
}
