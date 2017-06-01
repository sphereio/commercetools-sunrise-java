package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import io.sphere.sdk.products.ProductVariant;

import javax.inject.Inject;
import java.util.Locale;

public class WishlistItemViewModelFactory extends SimpleViewModelFactory<WishlistItemViewModel, ProductVariant> {
    private final Locale locale;

    @Inject
    protected WishlistItemViewModelFactory(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public final WishlistItemViewModel create(final ProductVariant input) {
        return super.create(input);
    }

    @Override
    protected WishlistItemViewModel newViewModelInstance(final ProductVariant input) {
        return new WishlistItemViewModel();
    }

    @Override
    protected final void initialize(final WishlistItemViewModel viewModel, final ProductVariant productVariant) {
        if (productVariant != null) {
            final String imageUrl = productVariant.getImages().stream()
                    .findFirst()
                    .map(image -> image.getUrl()).orElse(null);

            viewModel.setImageUrl(imageUrl);

            final String name = "";
            viewModel.setName(name);
        }
    }
}
