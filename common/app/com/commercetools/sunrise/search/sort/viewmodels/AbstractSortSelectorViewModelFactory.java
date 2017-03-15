package com.commercetools.sunrise.search.sort.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.search.sort.SortFormSettings;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.annotation.Nullable;

import static java.util.stream.Collectors.toList;

public abstract class AbstractSortSelectorViewModelFactory extends SimpleViewModelFactory<SortSelectorViewModel, PagedResult<?>> {

    private final SortFormSettings settings;
    private final SortFormSelectableOptionViewModelFactory sortFormSelectableOptionViewModelFactory;
    @Nullable
    private final String selectedOptionValue;

    protected AbstractSortSelectorViewModelFactory(final SortFormSettings settings,
                                                   final SortFormSelectableOptionViewModelFactory sortFormSelectableOptionViewModelFactory,
                                                   final Http.Request httpRequest) {
        this.selectedOptionValue = settings.getSelectedFieldValue(httpRequest);
        this.settings = settings;
        this.sortFormSelectableOptionViewModelFactory = sortFormSelectableOptionViewModelFactory;
    }

    @Nullable
    protected final String getSelectedOptionValue() {
        return selectedOptionValue;
    }

    protected final SortFormSettings getSettings() {
        return settings;
    }

    protected final SortFormSelectableOptionViewModelFactory getSortFormSelectableOptionViewModelFactory() {
        return sortFormSelectableOptionViewModelFactory;
    }

    @Override
    protected SortSelectorViewModel newViewModelInstance(final PagedResult<?> pagedResult) {
        return new SortSelectorViewModel();
    }

    @Override
    public SortSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }

    @Override
    protected final void initialize(final SortSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        fillKey(viewModel, pagedResult);
        fillList(viewModel, pagedResult);
    }

    protected void fillKey(final SortSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        viewModel.setKey(settings.getFieldName());
    }

    protected void fillList(final SortSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        viewModel.setList(settings.getOptions().stream()
                .map(option -> sortFormSelectableOptionViewModelFactory.create(option, selectedOptionValue))
                .collect(toList()));
    }
}
