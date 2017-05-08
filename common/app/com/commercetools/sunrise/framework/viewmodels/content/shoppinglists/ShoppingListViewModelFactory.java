package com.commercetools.sunrise.framework.viewmodels.content.shoppinglists;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Factory to create a {@link ShoppingListViewModel} from a {@link ShoppingList}.
 */
public class ShoppingListViewModelFactory extends SimpleViewModelFactory<ShoppingListViewModel, ShoppingList> {
    @Override
    protected ShoppingListViewModel newViewModelInstance(final ShoppingList input) {
        return new ShoppingListViewModel();
    }

    @Override
    protected void initialize(final ShoppingListViewModel viewModel, final ShoppingList input) {
        viewModel.setName(input.getName().get("en"));
    }
}
