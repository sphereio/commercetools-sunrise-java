package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.pagination.viewmodels.AbstractEntriesPerPageSelectorViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageSelectorViewModel;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductsPerPageSelectorViewModelFactory extends AbstractEntriesPerPageSelectorViewModelFactory {

    @Inject
    public ProductsPerPageSelectorViewModelFactory(final ProductsPerPageFormSettings settings,
                                                   final EntriesPerPageFormSelectableOptionViewModelFactory entriesPerPageFormSelectableOptionViewModelFactory,
                                                   final Http.Request httpRequest) {
        super(settings, entriesPerPageFormSelectableOptionViewModelFactory, httpRequest);
    }

    @Override
    public final EntriesPerPageSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }
}
