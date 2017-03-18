package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeUtils.optionsToFacetRange;
import static com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeUtils.optionsToFilterRange;

public interface BucketRangeFacetedSearchFormSettings<T> extends SimpleBucketRangeFacetedSearchFormSettings<T>, FacetedSearchFormSettings<T> {

    @Override
    default RangeFacetExpression<T> buildFacetExpression() {
        return RangeTermFacetSearchModel.<T, String>of(getAttributePath(), Function.identity())
                .withCountingProducts(true)
                .onlyRange(optionsToFacetRange(getOptions()));
    }

    @Override
    default List<FilterExpression<T>> buildFilterExpressions(final Http.Context httpContext) {
        final List<FilterRange<String>> selectedRanges = optionsToFilterRange(getAllSelectedOptions(httpContext));
        final RangeTermFacetedSearchSearchModel<T> filterSearchModel = RangeTermFacetedSearchSearchModel.of(getAttributePath());
        final RangeFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRanges.isEmpty()) {
            facetedSearchExpr = filterSearchModel.allRanges();
        } else if (isMatchingAll()) {
            facetedSearchExpr = filterSearchModel.isBetweenAll(selectedRanges);
        } else {
            facetedSearchExpr = filterSearchModel.isBetweenAny(selectedRanges);
        }
        return facetedSearchExpr.filterExpressions();
    }

    @Override
    default RangeFacetedSearchExpression<T> buildFacetedSearchExpression(final Http.Context httpContext) {
        final RangeFacetExpression<T> facetExpression = buildFacetExpression();
        final List<FilterExpression<T>> filterExpressions = buildFilterExpressions(httpContext);
        return RangeFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    default Optional<RangeFacetResult> findFacetResult(final PagedSearchResult<T> pagedSearchResult) {
        final RangeFacetExpression<T> facetExpression = buildFacetExpression();
        return Optional.ofNullable(pagedSearchResult.getFacetResult(facetExpression));
    }
}
