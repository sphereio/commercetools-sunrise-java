package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.events.ProductProjectionPagedSearchResultLoadedHook;
import com.commercetools.sunrise.framework.hooks.requests.ProductProjectionSearchHook;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchSelectorControllerComponent;
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
import static java.util.stream.Collectors.toList;

public final class ProductFacetedSearchSelectorControllerComponent extends AbstractFacetedSearchSelectorControllerComponent
        implements ControllerComponent, ProductProjectionSearchHook, ProductProjectionPagedSearchResultLoadedHook {

    private final Http.Request httpRequest;
    private final Locale locale;

    @Inject
    public ProductFacetedSearchSelectorControllerComponent(final ProductSearchSortFormSettings settings,
                                                           final ProductSearchSortSelectorViewModelFactory sortSelectorViewModelFactory,
                                                           final Http.Request httpRequest, final Locale locale) {
        super(settings, sortSelectorViewModelFactory);
        this.httpRequest = httpRequest;
        this.locale = locale;
    }

    @Override
    public ProductProjectionSearch onProductProjectionSearch(final ProductProjectionSearch search) {
        final List<SortExpression<ProductProjection>> expressions = getSelectedSortExpressions().stream()
                .map(SortExpression::<ProductProjection>of)
                .collect(toList());
        if (!expressions.isEmpty()) {
            return search.plusSort(expressions);
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
