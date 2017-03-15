package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import io.sphere.sdk.search.RangeFacetExpression;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.optionsToFacetRange;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.optionsToFilterRange;

public interface BucketRangeFacetedSearchFormSettings<T> extends MultiOptionFacetedSearchFormSettings<T>, FormSettingsWithOptions<BucketRangeFacetedSearchFormOption, String> {

    @Override
    default RangeFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale) {
        final String attributePath = getLocalizedAttributePath(locale);
        final List<FilterRange<String>> selectedRanges = optionsToFilterRange(findAllSelectedValuesFromQueryString(this, httpRequest));
        final RangeTermFacetedSearchSearchModel<T> filterSearchModel = RangeTermFacetedSearchSearchModel.of(attributePath);
        final RangeFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRanges.isEmpty()) {
            facetedSearchExpr = filterSearchModel.allRanges();
        } else if (isMatchingAll()) {
            facetedSearchExpr = filterSearchModel.isBetweenAll(selectedRanges);
        } else {
            facetedSearchExpr = filterSearchModel.isBetweenAny(selectedRanges);
        }
        final RangeFacetExpression<T> facetExpression = RangeTermFacetSearchModel.<T, String>of(attributePath, Function.identity())
                .withCountingProducts(true)
                .onlyRange(optionsToFacetRange(getOptions()));
        return RangeFacetedSearchExpression.of(facetExpression, facetedSearchExpr.filterExpressions());
    }

    static <T> BucketRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String attributePath, final int position,
                                                          final boolean isCountDisplayed, @Nullable final String uiType,
                                                          final boolean isMultiSelect, final boolean isMatchingAll,
                                                          final List<BucketRangeFacetedSearchFormOption> options) {
        return new BucketRangeFacetedSearchFormSettingsImpl<>(fieldName, label, attributePath, position, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, options);
    }
}
