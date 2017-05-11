package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistPaginationSettings;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistProductsPerPageFormSettings;
import com.commercetools.sunrise.search.pagination.viewmodels.AbstractPaginationViewModelFactory;
import com.google.inject.Inject;

public class WishlistPaginationViewModelFactory extends AbstractPaginationViewModelFactory {
    @Inject
    public WishlistPaginationViewModelFactory(final WishlistPaginationSettings paginationSettings,
                                              final WishlistProductsPerPageFormSettings entriesPerPageFormSettings) {
        super(paginationSettings, entriesPerPageFormSettings);
    }
}
