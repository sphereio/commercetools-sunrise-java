package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.search.SortExpression;
import play.mvc.Http;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public interface SortFormSettings<T> extends SimpleSortFormSettings, FormSettingsWithOptions<SortFormOption, List<String>> {

    default List<SortExpression<T>> buildSearchExpressions(final Http.Context httpContext, final Locale locale) {
        return getSelectedOption(httpContext)
                .map(option -> option.getLocalizedValue(locale).stream()
                    .map(SortExpression::<T>of)
                    .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    default List<QuerySort<T>> buildQueryExpressions(final Http.Context httpContext, final Locale locale) {
        return getSelectedOption(httpContext)
                .map(option -> option.getLocalizedValue(locale).stream()
                        .map(QuerySort::<T>of)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }
}
