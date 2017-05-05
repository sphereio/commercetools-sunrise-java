package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductVariantViewModel;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.LineItem;

/**
 * This factory class creates {@link WishlistProductViewModel}.
 */
public class WishlistProductViewModelFactory extends SimpleViewModelFactory<WishlistProductViewModel, LineItem> {
    private final WishlistProductVariantViewModelFactory productVariantViewModelFactory;

    @Inject
    public WishlistProductViewModelFactory(final WishlistProductVariantViewModelFactory productVariantViewModelFactory) {
        this.productVariantViewModelFactory = productVariantViewModelFactory;
    }

    @Override
    protected WishlistProductViewModel newViewModelInstance(final LineItem input) {
        return new WishlistProductViewModel();
    }

    @Override
    protected void initialize(final WishlistProductViewModel viewModel, final LineItem input) {
        final ProductVariantViewModel productVariantViewModel = productVariantViewModelFactory.create(input);
        viewModel.setVariant(productVariantViewModel);
    }
}
