package com.commercetools.sunrise.search.facetedsearch.bucketranges.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeFacetedSearchFormOption;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.AbstractFacetOptionViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.model.RangeStats;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class BucketRangeFacetOptionViewModelFactory extends AbstractFacetOptionViewModelFactory<RangeStats, BucketRangeFacetedSearchFormOption, List<String>> {

    private final MessagesResolver messagesResolver;

    @Inject
    public BucketRangeFacetOptionViewModelFactory(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    protected final MessagesResolver getMessagesResolver() {
        return messagesResolver;
    }

    @Override
    public final FacetOptionViewModel create(final RangeStats stats, final BucketRangeFacetedSearchFormOption value, @Nullable final List<String> selectedValue) {
        return super.create(stats, value, selectedValue);
    }

    @Override
    protected final void initialize(final FacetOptionViewModel viewModel, final RangeStats stats, final BucketRangeFacetedSearchFormOption value, @Nullable final List<String> selectedValue) {
        super.initialize(viewModel, stats, value, selectedValue);
    }

    @Override
    protected void fillLabel(final FacetOptionViewModel viewModel, final RangeStats stats, final BucketRangeFacetedSearchFormOption option, @Nullable final List<String> selectedValues) {
        viewModel.setLabel(messagesResolver.getOrKey(option.getFieldLabel()));
    }

    @Override
    protected void fillValue(final FacetOptionViewModel viewModel, final RangeStats stats, final BucketRangeFacetedSearchFormOption option, @Nullable final List<String> selectedValues) {
        viewModel.setValue(option.getFieldValue());
    }

    @Override
    protected void fillSelected(final FacetOptionViewModel viewModel, final RangeStats stats, final BucketRangeFacetedSearchFormOption option, @Nullable final List<String> selectedValues) {
        if (selectedValues != null) {
            viewModel.setSelected(selectedValues.contains(option.getFieldValue()));
        }
    }

    @Override
    protected void fillCount(final FacetOptionViewModel viewModel, final RangeStats stats, final BucketRangeFacetedSearchFormOption option, @Nullable final List<String> selectedValue) {
        viewModel.setCount(stats.getProductCount());
    }
}
