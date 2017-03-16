package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.events.ProductProjectionPagedSearchResultLoadedHook;
import com.commercetools.sunrise.framework.hooks.requests.ProductProjectionSearchHook;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchSelectorControllerComponent;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public final class ProductFacetedSearchSelectorControllerComponent extends AbstractFacetedSearchSelectorControllerComponent<ProductProjection>
        implements ControllerComponent, ProductProjectionSearchHook, ProductProjectionPagedSearchResultLoadedHook {

    private final List<FacetedSearchExpression<ProductProjection>> facetedSearchExpressions;

    @Nullable
    private PagedSearchResult<ProductProjection> pagedSearchResult;

    @Inject
    public ProductFacetedSearchSelectorControllerComponent(final ProductFacetedSearchFormSettingsList settings,
                                                           final ProductFacetSelectorListViewModelFactory productFacetSelectorListViewModelFactory,
                                                           final Http.Request httpRequest, final Locale locale) {
        super(settings, productFacetSelectorListViewModelFactory);
        this.facetedSearchExpressions = getSettings().buildFacetedSearchExpressions(httpRequest, locale);
    }

    @Nullable
    @Override
    protected PagedSearchResult<ProductProjection> getPagedSearchResult() {
        return pagedSearchResult;
    }

    @Override
    public ProductProjectionSearch onProductProjectionSearch(final ProductProjectionSearch search) {
        if (!facetedSearchExpressions.isEmpty()) {
            return search.plusFacetedSearch(facetedSearchExpressions);
        } else {
            return search;
        }
    }

    @Override
    public CompletionStage<?> onProductProjectionPagedSearchResultLoaded(final PagedSearchResult<ProductProjection> pagedSearchResult) {
        this.pagedSearchResult = pagedSearchResult;
        return completedFuture(null);
    }
}
