package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.WithFormFieldName;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.MultiOptionFacetedSearchFormSettings;
import io.sphere.sdk.search.FacetResult;
import io.sphere.sdk.search.FilteredFacetResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.model.RangeStats;

public abstract class AbstractFacetSelectorViewModelFactory<T extends FacetedSearchFormSettings<?>, F extends FacetResult> extends ViewModelFactory {

    private final I18nIdentifierResolver i18nIdentifierResolver;

    protected AbstractFacetSelectorViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    protected final I18nIdentifierResolver getI18nIdentifierResolver() {
        return i18nIdentifierResolver;
    }

    protected FacetViewModel newViewModelInstance(final T settings, final F facetResult) {
        return new FacetViewModel();
    }

    public FacetViewModel create(final T settings, final F facetResult) {
        final FacetViewModel viewModel = newViewModelInstance(settings, facetResult);
        initialize(viewModel, settings, facetResult);
        return viewModel;
    }

    protected void initialize(final FacetViewModel viewModel, final T settings, final F facetResult) {
        fillLabel(viewModel, settings, facetResult);
        fillAvailable(viewModel, settings, facetResult);
        fillCountHidden(viewModel, settings, facetResult);
        fillKey(viewModel, settings, facetResult);
        fillMultiSelect(viewModel, settings, facetResult);
        fillMatchingAll(viewModel, settings, facetResult);
        fillLimitedOptions(viewModel, settings, facetResult);
    }

    protected void fillLabel(final FacetViewModel viewModel, final T settings, final F facetResult) {
        viewModel.setLabel(i18nIdentifierResolver.resolveOrKey(settings.getLabel()));
    }

    protected void fillKey(final FacetViewModel viewModel, final T settings, final F facetResult) {
        if (settings instanceof WithFormFieldName) {
            viewModel.setKey(((WithFormFieldName) settings).getFieldName());
        }
    }

    protected void fillAvailable(final FacetViewModel viewModel, final T settings, final F facetResult) {
        Long totalMatching = null;
        if (facetResult instanceof RangeFacetResult) {
            totalMatching = ((RangeFacetResult) facetResult).getRanges().stream().mapToLong(RangeStats::getCount).sum();
        } else if (facetResult instanceof TermFacetResult) {
            totalMatching = ((TermFacetResult) facetResult).getTotal();
        } else if (facetResult instanceof FilteredFacetResult) {
            totalMatching = ((FilteredFacetResult) facetResult).getCount();
        }
        if (totalMatching != null) {
            viewModel.setAvailable(totalMatching > 0);
        }
    }

    protected void fillCountHidden(final FacetViewModel viewModel, final T settings, final F facetResult) {
        viewModel.setCountHidden(!settings.isCountDisplayed());
    }

    protected void fillMultiSelect(final FacetViewModel viewModel, final T settings, final F facetResult) {
        if (settings instanceof MultiOptionFacetedSearchFormSettings) {
            viewModel.setMultiSelect(((MultiOptionFacetedSearchFormSettings) settings).isMultiSelect());
        }
    }

    protected void fillMatchingAll(final FacetViewModel viewModel, final T settings, final F facetResult) {
        if (settings instanceof MultiOptionFacetedSearchFormSettings) {
            viewModel.setMatchingAll(((MultiOptionFacetedSearchFormSettings) settings).isMatchingAll());
        }
    }

    protected abstract void fillLimitedOptions(final FacetViewModel viewModel, final T settings, final F facetResult);
}
