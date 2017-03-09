package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.events.ProductProjectionPagedSearchResultLoadedHook;
import com.commercetools.sunrise.framework.hooks.requests.ProductProjectionSearchHook;
import com.commercetools.sunrise.search.sort.AbstractSortSelectorControllerComponent;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SortExpression;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class ProductSortSelectorControllerComponent extends AbstractSortSelectorControllerComponent<SortExpression<ProductProjection>>
        implements ControllerComponent, ProductProjectionSearchHook, ProductProjectionPagedSearchResultLoadedHook {

    @Inject
    public ProductSortSelectorControllerComponent(final ProductSortFormSettings settings,
                                                  final ProductSortSelectorViewModelFactory sortSelectorViewModelFactory,
                                                  final Http.Request httpRequest, final Locale locale) {
        super(settings, sortSelectorViewModelFactory, httpRequest, locale);
    }

    @Override
    public ProductProjectionSearch onProductProjectionSearch(final ProductProjectionSearch search) {
        final List<SortExpression<ProductProjection>> selectedSortExpressions = getSelectedSortExpressions();
        if (!selectedSortExpressions.isEmpty()) {
            return search.plusSort(selectedSortExpressions);
        } else {
            return search;
        }
    }

    @Override
    public CompletionStage<?> onProductProjectionPagedSearchResultLoaded(final PagedSearchResult<ProductProjection> pagedSearchResult) {
        setPagedResult(pagedSearchResult);
        return completedFuture(null);
    }
}
