package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.PageTitleResolver;
import com.commercetools.sunrise.framework.viewmodels.content.PageContentFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductThumbnailViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductThumbnailViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.content.products.ProductWithVariant;
import com.commercetools.sunrise.myaccount.wishlist.Wishlist;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedQueryResult;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The factory class for creating {@link WishlistPageContent}.
 */
public class WishlistPageContentFactory extends PageContentFactory<WishlistPageContent, Wishlist> {
    private final PageTitleResolver pageTitleResolver;
    private final ProductThumbnailViewModelFactory thumbnailViewModelFactory;

    @Inject
    public WishlistPageContentFactory(final PageTitleResolver pageTitleResolver, final ProductThumbnailViewModelFactory thumbnailViewModelFactory) {
        this.pageTitleResolver = pageTitleResolver;
        this.thumbnailViewModelFactory = thumbnailViewModelFactory;
    }

    @Override
    protected WishlistPageContent newViewModelInstance(final Wishlist input) {
        return new WishlistPageContent();
    }

    @Override
    protected void initialize(final WishlistPageContent viewModel, final Wishlist input) {
        super.initialize(viewModel, input);

        fillProducts(viewModel, input);
    }

    private void fillProducts(final WishlistPageContent viewModel, final Wishlist input) {
        final GenericListViewModel<ProductThumbnailViewModel> productList = new GenericListViewModel<>();
        final PagedQueryResult<ProductProjection> queryResult = input.getProducts();
        final List<ProductThumbnailViewModel> productThumbNails = queryResult == null ?
                Collections.emptyList() :
                queryResult.getResults().stream()
                    .map(product -> ProductWithVariant.of(product, product.getMasterVariant()))
                    .map(thumbnailViewModelFactory::create)
                    .collect(Collectors.toList());
        productList.setList(productThumbNails);
        viewModel.setProducts(productList);
    }


    @Override
    protected void fillTitle(final WishlistPageContent viewModel, final Wishlist input) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("my-account:myAccount.title"));
    }
}
