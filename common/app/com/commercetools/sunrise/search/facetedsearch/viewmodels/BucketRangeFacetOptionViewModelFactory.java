package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.BucketRangeFacetedSearchFormOption;
import io.sphere.sdk.search.model.RangeStats;

import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class BucketRangeFacetOptionViewModelFactory extends ViewModelFactory {

    private final I18nIdentifierResolver i18nIdentifierResolver;

    @Inject
    public BucketRangeFacetOptionViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    protected final I18nIdentifierResolver getI18nIdentifierResolver() {
        return i18nIdentifierResolver;
    }

    protected FacetOptionViewModel newViewModelInstance(final BucketRangeFacetedSearchFormOption option,
                                                        final RangeStats rangeStats, final List<String> selectedValues) {
        return new FacetOptionViewModel();
    }

    protected final void initialize(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                                    final RangeStats rangeStats, final List<String> selectedValues) {
        fillLabel(viewModel, option, rangeStats, selectedValues);
        fillValue(viewModel, option, rangeStats, selectedValues);
        fillSelected(viewModel, option, rangeStats, selectedValues);
        fillCount(viewModel, option, rangeStats, selectedValues);
    }

    protected void fillLabel(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final RangeStats rangeStats, final List<String> selectedValues) {
        viewModel.setLabel(i18nIdentifierResolver.resolveOrKey(option.getFieldLabel()));
    }

    protected void fillValue(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final RangeStats rangeStats, final List<String> selectedValues) {
        viewModel.setValue(option.getFieldValue());
    }

    protected void fillSelected(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                                final RangeStats rangeStats, final List<String> selectedValues) {
        viewModel.setSelected(selectedValues.contains(option.getFieldValue()));
    }

    protected void fillCount(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final RangeStats rangeStats, final List<String> selectedValues) {
        viewModel.setCount(rangeStats.getProductCount());
    }
}
