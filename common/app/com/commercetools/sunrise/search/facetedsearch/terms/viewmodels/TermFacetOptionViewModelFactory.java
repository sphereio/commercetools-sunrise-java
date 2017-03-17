package com.commercetools.sunrise.search.facetedsearch.terms.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.forms.SelectableViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.TermStats;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TermFacetOptionViewModelFactory extends SelectableViewModelFactory<FacetOptionViewModel, TermStats, List<String>> {

    @Override
    protected FacetOptionViewModel newViewModelInstance(final TermStats termStats, final List<String> selectedValues) {
        return new FacetOptionViewModel();
    }

    @Override
    public final FacetOptionViewModel create(final TermStats termStats, final List<String> selectedValues) {
        return super.create(termStats, selectedValues);
    }

    @Override
    protected final void initialize(final FacetOptionViewModel viewModel, final TermStats termStats, final List<String> selectedValues) {
        fillLabel(viewModel, termStats, selectedValues);
        fillValue(viewModel, termStats, selectedValues);
        fillSelected(viewModel, termStats, selectedValues);
        fillCount(viewModel, termStats, selectedValues);
    }

    protected void fillLabel(final FacetOptionViewModel viewModel, final TermStats termStats, final List<String> selectedValues) {
        viewModel.setLabel(termStats.getTerm());
    }

    protected void fillValue(final FacetOptionViewModel viewModel, final TermStats termStats, final List<String> selectedValues) {
        viewModel.setValue(termStats.getTerm());
    }

    protected void fillSelected(final FacetOptionViewModel viewModel, final TermStats termStats, final List<String> selectedValues) {
        viewModel.setSelected(selectedValues.contains(termStats.getTerm()));
    }

    protected void fillCount(final FacetOptionViewModel viewModel, final TermStats termStats, final List<String> selectedValues) {
        viewModel.setCount(termStats.getProductCount());
    }
}
