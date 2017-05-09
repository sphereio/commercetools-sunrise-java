package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductVariantViewModel;
import io.sphere.sdk.shoppinglists.LineItem;

import javax.inject.Inject;

/**
 * This factory class creates {@link ShoppingListProductViewModel}.
 */
public class ShoppingListProductViewModelFactory extends SimpleViewModelFactory<ShoppingListProductViewModel, LineItem> {
    private final ShoppingListProductVariantViewModelFactory productVariantViewModelFactory;

    @Inject
    public ShoppingListProductViewModelFactory(final ShoppingListProductVariantViewModelFactory productVariantViewModelFactory) {
        this.productVariantViewModelFactory = productVariantViewModelFactory;
    }

    @Override
    protected ShoppingListProductViewModel newViewModelInstance(final LineItem input) {
        return new ShoppingListProductViewModel();
    }

    @Override
    protected void initialize(final ShoppingListProductViewModel viewModel, final LineItem input) {
        final ProductVariantViewModel productVariantViewModel = productVariantViewModelFactory.create(input);
        viewModel.setVariant(productVariantViewModel);
    }
}
