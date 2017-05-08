package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.ViewModel;

/**
 * This view model is responsible for storing the wishlist.
 */
public class ShoppingListViewModel extends ViewModel {
    private String name;

    private GenericListViewModel<ShoppingListThumbnailViewModel> products;

    public GenericListViewModel<ShoppingListThumbnailViewModel> getProducts() {
        return products;
    }

    public void setProducts(final GenericListViewModel<ShoppingListThumbnailViewModel> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
