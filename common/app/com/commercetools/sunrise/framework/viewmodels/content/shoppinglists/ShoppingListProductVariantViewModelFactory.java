package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.framework.viewmodels.content.products.AbstractProductVariantViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductVariantViewModel;
import com.commercetools.sunrise.framework.viewmodels.formatters.PriceFormatter;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.shoppinglists.LineItem;

import javax.inject.Inject;

/**
 * The factory class for creating {@link ProductVariantViewModel} used by shopping lists.
 */
public class ShoppingListProductVariantViewModelFactory extends AbstractProductVariantViewModelFactory<LineItem> {
    private final PriceFormatter priceFormatter;
    private final ProductReverseRouter productReverseRouter;

    @Inject
    public ShoppingListProductVariantViewModelFactory(final PriceFormatter priceFormatter, final ProductReverseRouter productReverseRouter) {
        this.priceFormatter = priceFormatter;
        this.productReverseRouter = productReverseRouter;
    }

    @Override
    protected void fillSku(final ProductVariantViewModel viewModel, final LineItem input) {
        final ProductVariant variant = input.getVariant();
        viewModel.setSku(variant.getSku());
    }

    @Override
    protected void fillName(final ProductVariantViewModel viewModel, final LineItem input) {
        viewModel.setName(input.getName());
    }

    @Override
    protected void fillUrl(final ProductVariantViewModel viewModel, final LineItem input) {
    }

    @Override
    protected void fillImage(final ProductVariantViewModel viewModel, final LineItem input) {
        final ProductVariant variant = input.getVariant();
        findImageUrl(variant).ifPresent(viewModel::setImage);
    }

    @Override
    protected void fillPrice(final ProductVariantViewModel viewModel, final LineItem input) {
    }

    @Override
    protected void fillPriceOld(final ProductVariantViewModel viewModel, final LineItem input) {

    }

}
