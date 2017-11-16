package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.ConfiguredFacetedSearchFormSettings;

public abstract class AbstractFacetViewModelFactory<M extends FacetViewModel, T extends ConfiguredFacetedSearchFormSettings, F> extends ViewModelFactory {

    private final MessagesResolver messagesResolver;

    protected AbstractFacetViewModelFactory(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    protected final MessagesResolver getMessagesResolver() {
        return messagesResolver;
    }

    protected abstract M newViewModelInstance(final T settings, final F facetResult);

    public M create(final T settings, final F facetResult) {
        final M viewModel = newViewModelInstance(settings, facetResult);
        initialize(viewModel, settings, facetResult);
        return viewModel;
    }

    protected void initialize(final M viewModel, final T settings, final F facetResult) {
        fillLabel(viewModel, settings, facetResult);
        fillCountHidden(viewModel, settings, facetResult);
        fillAvailable(viewModel, settings, facetResult);
    }

    protected void fillLabel(final M viewModel, final T settings, final F facetResult) {
        viewModel.setLabel(messagesResolver.getOrKey(settings.getFieldLabel()));
    }

    protected void fillCountHidden(final M viewModel, final T settings, final F facetResult) {
        viewModel.setCountHidden(!settings.isCountDisplayed());
    }

    protected abstract void fillAvailable(final M viewModel, final T settings, final F facetResult);
}
