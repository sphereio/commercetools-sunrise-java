package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.sort.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.SortFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.search.sort.SortSelectorViewModel;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductSortSelectorViewModelFactory extends AbstractSortSelectorViewModelFactory {

    @Inject
    public ProductSortSelectorViewModelFactory(final ProductSearchSortFormSettings settings,
                                               final SortFormSelectableOptionViewModelFactory sortFormSelectableOptionViewModelFactory,
                                               final Http.Request httpRequest) {
        super(settings, sortFormSelectableOptionViewModelFactory, httpRequest);
    }

    @Override
    public final SortSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }
}
