package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.PagedResult;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static java.util.Collections.emptyList;

public abstract class AbstractSortSelectorControllerComponent<T> extends Base implements ControllerComponent, PageDataReadyHook {

    private final List<T> selectedSortExpressions;
    private final AbstractSortSelectorViewModelFactory<T> sortSelectorViewModelFactory;

    @Nullable
    private PagedResult<?> pagedResult;

    protected AbstractSortSelectorControllerComponent(final SortFormSettings<T> settings,
                                                      final AbstractSortSelectorViewModelFactory<T> sortSelectorViewModelFactory,
                                                      final Http.Request httpRequest, final Locale locale) {
        this.selectedSortExpressions = findSelectedValueFromQueryString(settings, httpRequest)
                .map(option -> option.getLocalizedValue(locale))
                .orElse(emptyList());
        this.sortSelectorViewModelFactory = sortSelectorViewModelFactory;
    }

    public final List<T> getSelectedSortExpressions() {
        return selectedSortExpressions;
    }

    @Nullable
    public final PagedResult<?> getPagedResult() {
        return pagedResult;
    }

    public final void setPagedResult(@Nullable final PagedResult<?> pagedResult) {
        this.pagedResult = pagedResult;
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        if (pagedResult != null && pageData.getContent() instanceof WithSortSelectorViewModel) {
            final WithSortSelectorViewModel content = (WithSortSelectorViewModel) pageData.getContent();
            content.setSortSelector(sortSelectorViewModelFactory.create(pagedResult));
        }
    }
}
