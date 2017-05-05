package com.commercetools.sunrise.framework.viewmodels.content.wishlist;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Factory to create a {@link WishlistViewModel} from a {@link ShoppingList}.
 */
public class WishlistViewModelFactory extends SimpleViewModelFactory<WishlistViewModel, ShoppingList> {
    @Override
    protected WishlistViewModel newViewModelInstance(final ShoppingList input) {
        return new WishlistViewModel();
    }

    @Override
    protected void initialize(final WishlistViewModel viewModel, final ShoppingList input) {
        viewModel.setName(input.getName().get("en"));
    }
}
