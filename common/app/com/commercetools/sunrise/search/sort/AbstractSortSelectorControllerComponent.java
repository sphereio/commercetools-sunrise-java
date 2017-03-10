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

public abstract class AbstractSortSelectorControllerComponent extends Base implements ControllerComponent, PageDataReadyHook {

    private final List<String> selectedSortExpressions;
    private final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory;

    @Nullable
    private PagedResult<?> pagedResult;

    protected AbstractSortSelectorControllerComponent(final SortFormSettings settings,
                                                      final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory,
                                                      final Http.Request httpRequest, final Locale locale) {
        this.selectedSortExpressions = findSelectedValueFromQueryString(settings, httpRequest)
                .map(option -> option.getLocalizedValue(locale))
                .orElse(emptyList());
        this.sortSelectorViewModelFactory = sortSelectorViewModelFactory;
    }

    protected final List<String> getSelectedSortExpressions() {
        return selectedSortExpressions;
    }

    @Nullable
    protected final PagedResult<?> getPagedResult() {
        return pagedResult;
    }

    protected final void setPagedResult(@Nullable final PagedResult<?> pagedResult) {
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
