package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.sort.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.SortFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.search.sort.SortSelectorViewModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedResult;
import io.sphere.sdk.search.SortExpression;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductSortSelectorViewModelFactory extends AbstractSortSelectorViewModelFactory<SortExpression<ProductProjection>> {

    @Inject
    public ProductSortSelectorViewModelFactory(final ProductSortFormSettings settings,
                                               final SortFormSelectableOptionViewModelFactory sortFormSelectableOptionViewModelFactory,
                                               final Http.Request httpRequest) {
        super(settings, sortFormSelectableOptionViewModelFactory, httpRequest);
    }

    @Override
    public final SortSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }
}
