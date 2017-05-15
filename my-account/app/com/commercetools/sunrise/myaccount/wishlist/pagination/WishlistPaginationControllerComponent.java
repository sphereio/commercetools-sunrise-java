package com.commercetools.sunrise.myaccount.wishlist.pagination;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.ctpevents.ProductProjectionPagedResultLoadedHook;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPaginationViewModelFactory;
import com.commercetools.sunrise.search.pagination.AbstractPaginationControllerComponent;
import com.google.inject.Inject;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedResult;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class WishlistPaginationControllerComponent extends AbstractPaginationControllerComponent
        implements ControllerComponent, ProductProjectionPagedResultLoadedHook {

    private PagedResult<ProductProjection> pagedResult;

    @Inject
    protected WishlistPaginationControllerComponent(final WishlistPaginationSettings paginationSettings,
                                                    final WishlistProductsPerPageFormSettings entriesPerPageFormSettings,
                                                    final WishlistPaginationViewModelFactory paginationViewModelFactory,
                                                    final WishlistProductsPerPageSelectorViewModelFactory entriesPerPageSelectorViewModelFactory) {
        super(paginationSettings, entriesPerPageFormSettings, paginationViewModelFactory, entriesPerPageSelectorViewModelFactory);
    }

    @Nullable
    @Override
    protected PagedResult<?> getPagedResult() {
        return pagedResult;
    }

    @Override
    public CompletionStage<?> onProductProjectionPagedResultLoaded(final PagedResult<ProductProjection> productProjectionPagedResult) {
        this.pagedResult = productProjectionPagedResult;
        return completedFuture(null);
    }
}
