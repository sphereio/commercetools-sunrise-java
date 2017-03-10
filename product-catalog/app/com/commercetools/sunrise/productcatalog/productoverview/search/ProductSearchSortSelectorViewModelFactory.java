package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.sort.viewmodels.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.viewmodels.SortFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.search.sort.viewmodels.SortSelectorViewModel;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductSearchSortSelectorViewModelFactory extends AbstractSortSelectorViewModelFactory {

    @Inject
    public ProductSearchSortSelectorViewModelFactory(final ProductSearchSortFormSettings settings,
                                                     final SortFormSelectableOptionViewModelFactory sortFormSelectableOptionViewModelFactory,
                                                     final Http.Request httpRequest) {
        super(settings, sortFormSelectableOptionViewModelFactory, httpRequest);
    }

    @Override
    public final SortSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }
}
