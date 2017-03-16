package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormSettings;
import io.sphere.sdk.search.TermFacetResult;

import javax.inject.Inject;

@RequestScoped
public class TermFacetSelectorViewModelFactory extends AbstractFacetSelectorViewModelFactory<TermFacetedSearchFormSettings<?>, TermFacetResult> {

    private final TermFacetViewModelFactory termFacetViewModelFactory;

    @Inject
    public TermFacetSelectorViewModelFactory(final TermFacetViewModelFactory termFacetViewModelFactory) {
        this.termFacetViewModelFactory = termFacetViewModelFactory;
    }

    protected final TermFacetViewModelFactory getTermFacetViewModelFactory() {
        return termFacetViewModelFactory;
    }

    @Override
    public final FacetSelectorViewModel create(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        return super.create(settings, facetResult);
    }

    @Override
    protected final void initialize(final FacetSelectorViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        super.initialize(viewModel, settings, facetResult);
        fillSelectFacet(viewModel, settings, facetResult);
        fillDisplayList(viewModel, settings, facetResult);
        fillHierarchicalSelectFacet(viewModel, settings, facetResult);
    }

    @Override
    protected void fillFacet(final FacetSelectorViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        viewModel.setFacet(termFacetViewModelFactory.create(settings, facetResult));
    }

    protected void fillSelectFacet(final FacetSelectorViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        if (settings.getUIType() != null) {
            viewModel.setSelectFacet(settings.getUIType().equals("list") || settings.getUIType().equals("columnsList"));
        }
    }

    protected void fillDisplayList(final FacetSelectorViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        if (settings.getUIType() != null) {
            viewModel.setDisplayList(settings.getUIType().equals("list"));
        }
    }

    protected void fillHierarchicalSelectFacet(final FacetSelectorViewModel viewModel, final TermFacetedSearchFormSettings<?> settings, final TermFacetResult facetResult) {
        if (settings.getUIType() != null) {
            viewModel.setHierarchicalSelectFacet(settings.getUIType().equals("categoryTree"));
        }
    }
}
