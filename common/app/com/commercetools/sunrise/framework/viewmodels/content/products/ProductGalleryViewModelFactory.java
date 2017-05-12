package com.commercetools.sunrise.framework.viewmodels.content.products;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.stream.Collectors.toList;

@Singleton
public class ProductGalleryViewModelFactory extends SimpleViewModelFactory<ProductGalleryViewModel, ProductWithVariant> {

    private final ProductImageViewModelFactory productImageViewModelFactory;

    @Inject
    public ProductGalleryViewModelFactory(final ProductImageViewModelFactory productImageViewModelFactory) {
        this.productImageViewModelFactory = productImageViewModelFactory;
    }

    protected final ProductImageViewModelFactory getProductImageViewModelFactory() {
        return productImageViewModelFactory;
    }

    @Override
    protected ProductGalleryViewModel newViewModelInstance(final ProductWithVariant productWithVariant) {
        return new ProductGalleryViewModel();
    }

    @Override
    public final ProductGalleryViewModel create(final ProductWithVariant productWithVariant) {
        return super.create(productWithVariant);
    }

    @Override
    protected final void initialize(final ProductGalleryViewModel viewModel, final ProductWithVariant productWithVariant) {
        fillList(viewModel, productWithVariant);
    }

    protected void fillList(final ProductGalleryViewModel viewModel, final ProductWithVariant productWithVariant) {
        viewModel.setList(productWithVariant.getVariant().getImages().stream()
                .map(productImageViewModelFactory::create)
                .collect(toList()));
    }
}
