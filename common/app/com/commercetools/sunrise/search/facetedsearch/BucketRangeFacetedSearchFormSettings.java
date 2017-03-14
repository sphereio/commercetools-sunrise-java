package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.RangeFacetExpression;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static java.util.stream.Collectors.toList;

public interface BucketRangeFacetedSearchFormSettings<T> extends FacetedSearchFormSettings<T>, FormSettingsWithOptions<BucketRangeFacetedSearchFormOption, String> {

    @Override
    default RangeFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale) {
        final RangeFacetExpression<T> facetExpression = RangeFacetExpression.of(getLocalizedExpression(locale));
        final List<BucketRangeFacetedSearchFormOption> selectedOptions = findAllSelectedValuesFromQueryString(this, httpRequest);
        final List<FilterExpression<T>> filterExpressions = selectedOptions.stream()
                .map(option -> FilterExpression.<T>of(option.getValue()))
                .collect(toList());
        return RangeFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    static <T> BucketRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String expression, final int position,
                                                          final FacetUIType type, final boolean isCountDisplayed, final boolean isMultiSelect,
                                                          final boolean isMatchingAll, @Nullable final FacetMapperSettings mapper,
                                                          final List<BucketRangeFacetedSearchFormOption> options) {
        return new BucketRangeFacetedSearchFormSettingsImpl<>(fieldName, label, expression, position, type, isCountDisplayed, isMultiSelect, isMatchingAll, mapper, options);
    }
}
