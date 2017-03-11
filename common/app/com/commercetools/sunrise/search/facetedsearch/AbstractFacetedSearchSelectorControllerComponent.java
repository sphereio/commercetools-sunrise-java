package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.consumers.PageDataReadyHook;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.search.sort.viewmodels.AbstractSortSelectorViewModelFactory;
import com.commercetools.sunrise.search.sort.viewmodels.WithSortSelectorViewModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.PagedResult;
import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public abstract class AbstractFacetedSearchSelectorControllerComponent<T> extends Base implements ControllerComponent, PageDataReadyHook {

    private final List<FacetedSearchFormSettings> settings;
    private final List<FacetedSearchExpression<T>> facetedSearchExpressions;
    private final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory;

    @Nullable
    private PagedSearchResult<?> pagedResult;
    private List<FacetedSearchFormSettingsWithOptions<?>> settingsWithOptions;

    protected AbstractFacetedSearchSelectorControllerComponent(final List<FacetedSearchFormSettings> settings,
                                                               final AbstractSortSelectorViewModelFactory sortSelectorViewModelFactory,
                                                               final Http.Request httpRequest, final Locale locale) {

        this.settings = settings;
        this.facetedSearchExpressions = settings.stream()
                .map(setting -> setting.getFacetedSearchExpression(httpRequest, locale))
                .collect(toList());
        this.sortSelectorViewModelFactory = sortSelectorViewModelFactory;
    }

    protected final List<FacetedSearchExpression<T>> getFacetedSearchExpressions() {
        return facetedSearchExpressions;
    }

    @Nullable
    protected final PagedResult<?> getPagedResult() {
        return pagedResult;
    }

    protected final void setPagedResult(@Nullable final PagedResult<?> pagedResult) {
        this.pagedResult = pagedResult;
    }

    private FacetedSearchExpression<T> getFacetedSearchExpression(final FacetedSearchFormSettingsWithOptions<?> setting, final Http.Request httpRequest, final Locale locale) {
        final List<String> selectedValues = setting.getAllSelectedValues(httpRequest);
        final FacetedSearchSearchModel<T> facetedSearchModel = RangeTermFacetedSearchSearchModel.of(setting.getLocalizedExpression(locale));
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = facetedSearchModel.allTerms();
        } else if (setting.isMatchingAll()) {
            facetedSearchExpr = facetedSearchModel.containsAll(selectedValues);
        } else {
            facetedSearchExpr = facetedSearchModel.containsAny(selectedValues);
        }
        return facetedSearchExpr;
    }

    protected final void setX() {
        settings.stream()
                .map(setting -> {
                    if (setting instanceof TermFacetedSearchFormSettings) {
                        TermFacetedSearchFormSettingsWithOptions.of(setting, pagedResult.)
                        TermFacetedSearchSearchModel.of(getLocalizedExpression(locale));
                    })
    }

    @Override
    public void onPageDataReady(final PageData pageData) {
        if (pagedResult != null && pageData.getContent() instanceof WithSortSelectorViewModel) {
            final WithSortSelectorViewModel content = (WithSortSelectorViewModel) pageData.getContent();
            content.setSortSelector(sortSelectorViewModelFactory.create(pagedResult));
        }
    }
}
