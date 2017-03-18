package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface TermFacetedSearchFormSettings<T> extends SimpleTermFacetedSearchFormSettings<T>, FacetedSearchFormSettings<T> {

    @Override
    default TermFacetedSearchExpression<T> buildFacetedSearchExpression(final Http.Context httpContext) {
        final TermFacetExpression<T> facetExpression = buildFacetExpression();
        final List<FilterExpression<T>> filterExpressions = buildFilterExpressions(httpContext);
        return TermFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    @Override
    default List<FilterExpression<T>> buildFilterExpressions(final Http.Context httpContext) {
        final List<String> selectedValues = getAllSelectedValues(httpContext);
        final FacetedSearchSearchModel<T> searchModel = TermFacetedSearchSearchModel.of(getAttributePath());
        final TermFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = searchModel.allTerms();
        } else if (isMatchingAll()) {
            facetedSearchExpr = searchModel.containsAll(selectedValues);
        } else {
            facetedSearchExpr = searchModel.containsAny(selectedValues);
        }
        return facetedSearchExpr.filterExpressions();
    }

    @Override
    default TermFacetExpression<T> buildFacetExpression() {
        return TermFacetSearchModel.<T, String>of(getAttributePath(), Function.identity())
                .withCountingProducts(true)
                .allTerms();
    }

    default Optional<TermFacetResult> findFacetResult(final PagedSearchResult<T> pagedSearchResult) {
        final TermFacetExpression<T> facetExpression = buildFacetExpression();
        return Optional.ofNullable(pagedSearchResult.getFacetResult(facetExpression));
    }
}
