package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.BucketRangeFacetedSearchFormOption;
import io.sphere.sdk.search.RangeFacetResult;

import javax.inject.Inject;
import java.util.List;

import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.optionToFacetRange;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.parseFacetRange;

@RequestScoped
public class BucketRangeFacetViewModelFactory extends ViewModelFactory {

    private final I18nIdentifierResolver i18nIdentifierResolver;

    @Inject
    public BucketRangeFacetViewModelFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    protected final I18nIdentifierResolver getI18nIdentifierResolver() {
        return i18nIdentifierResolver;
    }

    protected FacetOptionViewModel newViewModelInstance(final BucketRangeFacetedSearchFormOption option,
                                                        final List<String> selectedValues, final RangeFacetResult facetResult) {
        return new FacetOptionViewModel();
    }

    protected final void initialize(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                                    final List<String> selectedValues, final RangeFacetResult facetResult) {
        fillLabel(viewModel, option, selectedValues, facetResult);
        fillValue(viewModel, option, selectedValues, facetResult);
        fillSelected(viewModel, option, selectedValues, facetResult);
        fillCount(viewModel, option, selectedValues, facetResult);
    }

    protected void fillLabel(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final List<String> selectedValues, final RangeFacetResult facetResult) {
        viewModel.setLabel(i18nIdentifierResolver.resolveOrKey(option.getFieldLabel()));
    }

    protected void fillValue(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final List<String> selectedValues, final RangeFacetResult facetResult) {
        viewModel.setValue(option.getFieldValue());
    }

    protected void fillSelected(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                                final List<String> selectedValues, final RangeFacetResult facetResult) {
        viewModel.setSelected(selectedValues.contains(option.getFieldValue()));
    }

    protected void fillCount(final FacetOptionViewModel viewModel, final BucketRangeFacetedSearchFormOption option,
                             final List<String> selectedValues, final RangeFacetResult facetResult) {
        optionToFacetRange(option)
                .ifPresent(optionRange -> facetResult.getRanges().stream()
                        .filter(range -> parseFacetRange(range.getLowerEndpoint(), range.getUpperEndpoint())
                                .map(facetRange -> facetRange.equals(optionRange))
                                .orElse(false))
                        .findAny()
                        .ifPresent(rangeStats -> viewModel.setCount(rangeStats.getProductCount())));
    }
}
