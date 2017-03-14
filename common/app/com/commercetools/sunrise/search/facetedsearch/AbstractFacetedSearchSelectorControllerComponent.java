package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapper;
import com.commercetools.sunrise.search.sort.viewmodels.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.viewmodels.WithSortSelectorViewModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.TermFacetedSearchExpression;
import play.inject.Injector;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractFacetedSearchSelectorControllerComponent<T> extends Base implements ControllerComponent, PageDataReadyHook {

    private final FacetedSearchFormSettingsList<T> settings;
    private final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory;
    private final Injector injector;

    @Nullable
    private PagedSearchResult<T> pagedSearchResult;

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
    protected final PagedSearchResult<T> getPagedSearchResult() {
        return pagedSearchResult;
    }

    protected final void setPagedSearchResult(@Nullable final PagedSearchResult<T> pagedSearchResult) {
        this.pagedSearchResult = pagedSearchResult;
    }

    private TermFacetedSearchFormSettingsWithOptions<T> createSettingsWithOptions(final TermFacetedSearchFormSettings<T> setting,
                                                                               final TermFacetedSearchExpression<T> expression,
                                                                               final PagedSearchResult<T> pagedSearchResult) {
        final TermFacetResult termFacetResult = pagedSearchResult.getFacetResult(expression);
        final TermFacetMapper termFacetMapper = Optional.ofNullable(setting.getMapperSettings())
                .flatMap(mapperSettings -> Optional.ofNullable(mapperSettings.getType().factory()))
                .map(factoryClass -> injector.instanceOf(factoryClass).create(setting.getMapperSettings()))
                .filter(facetMapper -> facetMapper instanceof TermFacetMapper)
                .map(facetMapper -> (TermFacetMapper) facetMapper)
                .orElse(null);
        return TermFacetedSearchFormSettingsWithOptions.ofFacetResult(setting, termFacetResult, termFacetMapper);
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        if (pagedSearchResult != null && pageData.getContent() instanceof WithSortSelectorViewModel) {
            final WithSortSelectorViewModel content = (WithSortSelectorViewModel) pageData.getContent();
            content.setSortSelector(sortSelectorViewModelFactory.create(pagedSearchResult));
        }
    }
}
