package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.pagination.viewmodels.AbstractEntriesPerPageSelectorViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageFormSelectableOptionViewModelFactory;
import play.mvc.Http;

import javax.inject.Inject;

public class ProductsPerPageSelectorViewModelFactory extends AbstractEntriesPerPageSelectorViewModelFactory {

    @Inject
    public ProductsPerPageSelectorViewModelFactory(final ProductsPerPageFormSettings settings,
                                                   final EntriesPerPageFormSelectableOptionViewModelFactory entriesPerPageFormSelectableOptionViewModelFactory,
                                                   final Http.Request httpRequest) {
        super(settings, entriesPerPageFormSelectableOptionViewModelFactory, httpRequest);
    }
}
