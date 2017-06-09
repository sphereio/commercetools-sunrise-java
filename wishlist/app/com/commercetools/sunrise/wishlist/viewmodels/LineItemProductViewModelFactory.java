package com.commercetools.sunrise.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductGalleryViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductViewModel;
import io.sphere.sdk.shoppinglists.LineItem;

import javax.inject.Inject;

public class LineItemProductViewModelFactory extends SimpleViewModelFactory<ProductViewModel, LineItem> {
    private final LineItemProductVariantViewModelFactory productVariantViewModelFactory;
    private final ProductGalleryViewModelFactory productGalleryViewModelFactory;

    @Inject
    protected LineItemProductViewModelFactory(final LineItemProductVariantViewModelFactory productVariantViewModelFactory,
                                              final ProductGalleryViewModelFactory productGalleryViewModelFactory) {
        this.productVariantViewModelFactory = productVariantViewModelFactory;
        this.productGalleryViewModelFactory = productGalleryViewModelFactory;
    }

    @Override
    protected ProductViewModel newViewModelInstance(final LineItem input) {
        return new ProductViewModel();
    }

    @Override
    protected void initialize(final ProductViewModel viewModel, final LineItem input) {
        viewModel.setProductId(input.getProductId());
        viewModel.setVariantId(input.getVariantId());

        viewModel.setVariant(productVariantViewModelFactory.create(input));
        viewModel.setGallery(productGalleryViewModelFactory.create(input.getVariant()));

        viewModel.put("lineItemId", input.getId());
    }
}
