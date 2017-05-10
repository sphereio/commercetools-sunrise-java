package com.commercetools.sunrise.myaccount.wishlist.pagination;

import com.commercetools.sunrise.search.pagination.viewmodels.AbstractEntriesPerPageSelectorViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageFormSelectableOptionViewModelFactory;

import javax.inject.Inject;

public final class WishlistProductsPerPageSelectorViewModelFactory extends AbstractEntriesPerPageSelectorViewModelFactory {

    @Inject
    public WishlistProductsPerPageSelectorViewModelFactory(final WishlistProductsPerPageFormSettings productsPerPageFormSettings,
                                                           final EntriesPerPageFormSelectableOptionViewModelFactory entriesPerPageFormSelectableOptionViewModelFactory) {
        super(productsPerPageFormSettings, entriesPerPageFormSelectableOptionViewModelFactory);
    }
}
