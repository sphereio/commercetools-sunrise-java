package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.pagination.viewmodels.AbstractPaginationViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.PaginationViewModel;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductPaginationViewModelFactory extends AbstractPaginationViewModelFactory {

    @Inject
    public ProductPaginationViewModelFactory(final ProductPaginationSettings settings, final Http.Request httpRequest) {
        super(settings, httpRequest);
    }

    @Override
    public final PaginationViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }
}
