package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.search.facetedsearch.ConfiguredFacetedSearchFormSettingsWithOptions;

public abstract class AbstractFacetWithOptionsViewModelFactory<M extends FacetWithOptionsViewModel, T extends ConfiguredFacetedSearchFormSettingsWithOptions, F> extends AbstractFacetViewModelFactory<M, T, F> {

    protected AbstractFacetWithOptionsViewModelFactory(final MessagesResolver messagesResolver) {
        super(messagesResolver);
    }

    @Override
    protected void initialize(final M viewModel, final T settings, final F facetResult) {
        super.initialize(viewModel, settings, facetResult);
        fillMultiSelect(viewModel, settings, facetResult);
        fillMatchingAll(viewModel, settings, facetResult);
        fillLimitedOptions(viewModel, settings, facetResult);
    }

    protected void fillMultiSelect(final M viewModel, final T settings, final F facetResult) {
        viewModel.setMultiSelect(settings.isMultiSelect());
    }

    protected void fillMatchingAll(final M viewModel, final T settings, final F facetResult) {
        viewModel.setMatchingAll(settings.isMatchingAll());
    }

    protected abstract void fillLimitedOptions(final M viewModel, final T settings, final F facetResult);
}
