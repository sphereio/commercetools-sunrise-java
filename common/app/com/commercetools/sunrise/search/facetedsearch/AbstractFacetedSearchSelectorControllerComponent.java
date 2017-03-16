package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.search.sort.viewmodels.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.viewmodels.WithSortSelectorViewModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.PagedSearchResult;
import play.inject.Injector;

import javax.annotation.Nullable;

public abstract class AbstractFacetedSearchSelectorControllerComponent<T> extends Base implements ControllerComponent, PageDataReadyHook {

    private final FacetedSearchFormSettingsList<T> settings;
    private final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory;
    private final Injector injector;

    protected AbstractFacetedSearchSelectorControllerComponent(final FacetedSearchFormSettingsList<T> settings,
                                                               final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory,
                                                               final Injector injector) {
        this.settings = settings;
        this.sortSelectorViewModelFactory = sortSelectorViewModelFactory;
        this.injector = injector;
    }

    protected final FacetedSearchFormSettingsList<T> getSettings() {
        return settings;
    }

    @Nullable
    protected abstract PagedSearchResult<T> getPagedSearchResult();

    @Override
    public void onPageDataReady(final PageData pageData) {
        final PagedSearchResult<T> pagedSearchResult = getPagedSearchResult();
        if (pagedSearchResult != null && pageData.getContent() instanceof WithSortSelectorViewModel) {
            final WithSortSelectorViewModel content = (WithSortSelectorViewModel) pageData.getContent();
            content.setSortSelector(sortSelectorViewModelFactory.create(pagedSearchResult));
        }
    }
}
