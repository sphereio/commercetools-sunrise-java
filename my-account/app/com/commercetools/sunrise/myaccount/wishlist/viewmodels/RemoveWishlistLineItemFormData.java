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
@ImplementedBy(DefaultRemoveWishlistLineItemFormData.class)
public interface RemoveWishlistLineItemFormData {

    String lineItemId();
}
