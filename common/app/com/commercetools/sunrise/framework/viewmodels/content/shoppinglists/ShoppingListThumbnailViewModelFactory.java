package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.LineItem;

public class ShoppingListThumbnailViewModelFactory extends SimpleViewModelFactory<ShoppingListThumbnailViewModel, LineItem> {
    private final ShoppingListProductViewModelFactory productViewModelFactory;

    @Inject
    public ShoppingListThumbnailViewModelFactory(final ShoppingListProductViewModelFactory productViewModelFactory) {
        this.productViewModelFactory = productViewModelFactory;
    }

    @Override
    protected ShoppingListThumbnailViewModel newViewModelInstance(final LineItem input) {
        return new ShoppingListThumbnailViewModel();
    }

    @Override
    protected void initialize(final ShoppingListThumbnailViewModel viewModel, final LineItem input) {
        viewModel.setProduct(productViewModelFactory.create(input));
    }
}
