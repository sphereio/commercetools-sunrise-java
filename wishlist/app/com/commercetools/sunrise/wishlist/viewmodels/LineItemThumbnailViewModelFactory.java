package com.commercetools.sunrise.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductThumbnailViewModel;
import io.sphere.sdk.shoppinglists.LineItem;

import javax.inject.Inject;

public class LineItemThumbnailViewModelFactory extends SimpleViewModelFactory<ProductThumbnailViewModel, LineItem> {
    private final LineItemProductViewModelFactory lineItemProductViewModelFactory;

    @Inject
    protected LineItemThumbnailViewModelFactory(final LineItemProductViewModelFactory lineItemProductViewModelFactory) {
        this.lineItemProductViewModelFactory = lineItemProductViewModelFactory;
    }

    @Override
    protected ProductThumbnailViewModel newViewModelInstance(final LineItem input) {
        return new ProductThumbnailViewModel();
    }

    @Override
    protected void initialize(final ProductThumbnailViewModel viewModel, final LineItem input) {
        viewModel.setProduct(lineItemProductViewModelFactory.create(input));
    }
}
