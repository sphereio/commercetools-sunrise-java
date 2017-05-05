package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.LineItem;

public class WishlistThumbnailViewModelFactory extends SimpleViewModelFactory<WishlistThumbnailViewModel, LineItem> {
    private final WishlistProductViewModelFactory productViewModelFactory;

    @Inject
    public WishlistThumbnailViewModelFactory(final WishlistProductViewModelFactory productViewModelFactory) {
        this.productViewModelFactory = productViewModelFactory;
    }

    @Override
    protected WishlistThumbnailViewModel newViewModelInstance(final LineItem input) {
        return new WishlistThumbnailViewModel();
    }

    @Override
    protected void initialize(final WishlistThumbnailViewModel viewModel, final LineItem input) {
        viewModel.setProduct(productViewModelFactory.create(input));
    }
}
