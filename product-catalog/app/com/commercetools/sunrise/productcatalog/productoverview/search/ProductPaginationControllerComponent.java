package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.events.ProductProjectionPagedSearchResultLoadedHook;
import com.commercetools.sunrise.framework.hooks.requests.ProductProjectionSearchHook;
import com.commercetools.sunrise.search.pagination.AbstractPaginationControllerComponent;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.PagedSearchResult;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class ProductPaginationControllerComponent extends AbstractPaginationControllerComponent
        implements ControllerComponent, ProductProjectionSearchHook, ProductProjectionPagedSearchResultLoadedHook {

    @Inject
    ProductPaginationControllerComponent(final Http.Request httpRequest, final ProductPaginationSettings paginationSettings,
                                         final ProductsPerPageFormSettings elementsPerPageFormSettings,
                                         final ProductPaginationViewModelFactory paginationViewModelFactory,
                                         final ProductsPerPageSelectorViewModelFactory elementsPerPageSelectorViewModelFactory) {
        super(paginationSettings, elementsPerPageFormSettings, paginationViewModelFactory, elementsPerPageSelectorViewModelFactory, httpRequest);
    }

    @Override
    public ProductProjectionSearch onProductProjectionSearch(final ProductProjectionSearch search) {
        return search
                .withOffset(getOffset())
                .withLimit(getLimit());
    }

    @Override
    public CompletionStage<?> onProductProjectionPagedSearchResultLoaded(final PagedSearchResult<ProductProjection> pagedSearchResult) {
        setPagedResult(pagedSearchResult);
        return completedFuture(null);
    }
}
