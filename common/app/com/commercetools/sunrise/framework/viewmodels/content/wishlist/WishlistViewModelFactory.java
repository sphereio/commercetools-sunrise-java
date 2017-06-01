package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class WishlistViewModelFactory extends SimpleViewModelFactory<WishlistViewModel, ShoppingList> {
    private WishlistItemViewModelFactory wishlistItemViewModelFactory;

    private final Locale locale;

    @Inject
    protected WishlistViewModelFactory(final WishlistItemViewModelFactory wishlistItemViewModelFactory, final Locale locale) {
        this.wishlistItemViewModelFactory = wishlistItemViewModelFactory;
        this.locale = locale;
    }

    @Override
    public final WishlistViewModel create(final ShoppingList input) {
        return super.create(input);
    }

    @Override
    protected WishlistViewModel newViewModelInstance(final ShoppingList input) {
        return new WishlistViewModel();
    }

    @Override
    protected final void initialize(final WishlistViewModel viewModel, final ShoppingList wishlist) {
        if (wishlist != null) {
            final String title = wishlist.getName().get(locale);
            viewModel.setTitle(title);

            final List<LineItem> lineItems = new ArrayList<>(wishlist.getLineItems());
            Collections.reverse(lineItems);
            viewModel.setQuantity(lineItems.size());

            final int lastIndex = lineItems.size() > 3 ? 3 : lineItems.size();

            final List<LineItem> recentlyAdded = lineItems.subList(0, lastIndex);
            final List<WishlistItemViewModel> wishlistItemViewModels = recentlyAdded.stream()
                    .map(LineItem::getVariant)
                    .map(wishlistItemViewModelFactory::create)
                    .collect(Collectors.toList());

            viewModel.setList(wishlistItemViewModels);
        }
    }
}
