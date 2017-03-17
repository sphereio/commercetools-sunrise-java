package com.commercetools.sunrise.search.facetedsearch.bucketranges;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsWithOptions;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeUtils.optionsToFacetRange;
import static com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeUtils.optionsToFilterRange;

public interface BucketRangeFacetedSearchFormSettings<T> extends FacetedSearchFormSettingsWithOptions<T>, FormSettingsWithOptions<BucketRangeFacetedSearchFormOption, String> {

    @Override
    default RangeFacetedSearchExpression<T> buildFacetedSearchExpression(final Locale locale, final Http.Request httpRequest) {
        final List<FilterExpression<T>> filterExpressions = buildFilterExpressions(locale, httpRequest);
        final RangeFacetExpression<T> facetExpression = buildFacetExpression(locale);
        return RangeFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    @Override
    default List<FilterExpression<T>> buildFilterExpressions(final Locale locale, final Http.Request httpRequest) {
        final List<FilterRange<String>> selectedRanges = optionsToFilterRange(findAllSelectedValuesFromQueryString(this, httpRequest));
        final RangeTermFacetedSearchSearchModel<T> filterSearchModel = RangeTermFacetedSearchSearchModel.of(getLocalizedAttributePath(locale));
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
    default RangeFacetExpression<T> buildFacetExpression(final Locale locale) {
        return RangeTermFacetSearchModel.<T, String>of(getLocalizedAttributePath(locale), Function.identity())
                .withCountingProducts(true)
                .onlyRange(optionsToFacetRange(getOptions()));
    }

    default Optional<RangeFacetResult> findFacetResult(final PagedSearchResult<T> pagedSearchResult, final Locale locale) {
        final RangeFacetExpression<T> facetExpression = buildFacetExpression(locale);
        return Optional.ofNullable(pagedSearchResult.getFacetResult(facetExpression));
    }

    static <T> BucketRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String attributePath,
                                                          final boolean isCountDisplayed, @Nullable final String uiType,
                                                          final boolean isMultiSelect, final boolean isMatchingAll,
                                                          final List<BucketRangeFacetedSearchFormOption> options) {
        return new BucketRangeFacetedSearchFormSettingsImpl<>(fieldName, label, attributePath, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, options);
    }
}
