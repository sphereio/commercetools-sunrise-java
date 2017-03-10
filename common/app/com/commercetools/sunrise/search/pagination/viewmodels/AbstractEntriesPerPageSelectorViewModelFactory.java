package com.commercetools.sunrise.search.pagination.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.search.pagination.EntriesPerPageFormOption;
import com.commercetools.sunrise.search.pagination.EntriesPerPageFormSettings;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageFormSelectableOptionViewModelFactory;
import com.commercetools.sunrise.search.pagination.viewmodels.EntriesPerPageSelectorViewModel;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.annotation.Nullable;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static java.util.stream.Collectors.toList;

public abstract class AbstractEntriesPerPageSelectorViewModelFactory extends ViewModelFactory<EntriesPerPageSelectorViewModel, PagedResult<?>> {

    @Nullable
    private final String selectedOptionValue;
    private final EntriesPerPageFormSettings settings;
    private final EntriesPerPageFormSelectableOptionViewModelFactory entriesPerPageFormSelectableOptionViewModelFactory;

    protected AbstractEntriesPerPageSelectorViewModelFactory(final EntriesPerPageFormSettings settings,
                                                             final EntriesPerPageFormSelectableOptionViewModelFactory entriesPerPageFormSelectableOptionViewModelFactory,
                                                             final Http.Request httpRequest) {
        this.selectedOptionValue = findSelectedValueFromQueryString(settings, httpRequest)
                .map(EntriesPerPageFormOption::getFieldValue)
                .orElse(null);
        this.settings = settings;
        this.entriesPerPageFormSelectableOptionViewModelFactory = entriesPerPageFormSelectableOptionViewModelFactory;
    }

    @Nullable
    protected final String getSelectedOptionValue() {
        return selectedOptionValue;
    }

    protected final EntriesPerPageFormSettings getSettings() {
        return settings;
    }

    protected final EntriesPerPageFormSelectableOptionViewModelFactory getEntriesPerPageFormSelectableOptionViewModelFactory() {
        return entriesPerPageFormSelectableOptionViewModelFactory;
    }

    @Override
    public EntriesPerPageSelectorViewModel create(final PagedResult<?> pagedResult) {
        return super.create(pagedResult);
    }

    @Override
    protected EntriesPerPageSelectorViewModel newViewModelInstance(final PagedResult<?> pagedResult) {
        return new EntriesPerPageSelectorViewModel();
    }

    @Override
    protected final void initialize(final EntriesPerPageSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        fillKey(viewModel, pagedResult);
        fillList(viewModel, pagedResult);
    }

    protected void fillKey(final EntriesPerPageSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        viewModel.setKey(settings.getFieldName());
    }

    protected void fillList(final EntriesPerPageSelectorViewModel viewModel, final PagedResult<?> pagedResult) {
        viewModel.setList(settings.getOptions().stream()
                .map(option -> entriesPerPageFormSelectableOptionViewModelFactory.create(option, selectedOptionValue))
                .collect(toList()));
    }
}
