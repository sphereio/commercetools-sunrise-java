package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

public class ShoppingListThumbnailViewModel extends ViewModel {
    private ShoppingListProductViewModel product;

    private String lineItemId;

    public ShoppingListProductViewModel getProduct() {
        return product;
    }

    public void setProduct(final ShoppingListProductViewModel product) {
        this.product = product;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(final String lineItemId) {
        this.lineItemId = lineItemId;
    }
}
