package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.GenericListViewModel;
import com.commercetools.sunrise.framework.viewmodels.PageTitleResolver;
import com.commercetools.sunrise.framework.viewmodels.content.PageContentFactory;
import com.commercetools.sunrise.framework.viewmodels.content.shoppinglists.ShoppingListThumbnailViewModel;
import com.commercetools.sunrise.framework.viewmodels.content.shoppinglists.ShoppingListThumbnailViewModelFactory;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The factory class fore creating {@link WishlistPageContent}.
 */
public class WishlistPageContentFactory extends PageContentFactory<WishlistPageContent, ShoppingList> {
    private final PageTitleResolver pageTitleResolver;
    private final ShoppingListThumbnailViewModelFactory thumbnailViewModelFactory;

    @Inject
    public WishlistPageContentFactory(final PageTitleResolver pageTitleResolver, final ShoppingListThumbnailViewModelFactory thumbnailViewModelFactory) {
        this.pageTitleResolver = pageTitleResolver;
        this.thumbnailViewModelFactory = thumbnailViewModelFactory;
    }

    @Override
    protected WishlistPageContent newViewModelInstance(final ShoppingList input) {
        return new WishlistPageContent();
    }

    @Override
    protected void initialize(final WishlistPageContent viewModel, final ShoppingList input) {
        super.initialize(viewModel, input);
        final GenericListViewModel<ShoppingListThumbnailViewModel> products = new GenericListViewModel<>();
        final List<ShoppingListThumbnailViewModel> list = input.getLineItems().stream()
                .map(thumbnailViewModelFactory::create)
                .collect(Collectors.toList());
        products.setList(list);
        viewModel.setProducts(products);
    }

    @Override
    protected void fillTitle(final WishlistPageContent viewModel, final ShoppingList input) {
        viewModel.setTitle(pageTitleResolver.getOrEmpty("my-account:myAccount.title"));
    }
}
