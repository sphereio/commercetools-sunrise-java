package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.search.SortExpression;
import play.mvc.Http;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static java.util.stream.Collectors.toList;

public interface SortFormSettings<T> extends FormSettingsWithOptions<SortFormOption, List<String>> {

    default List<SortExpression<T>> buildSearchExpressions(final Http.Request httpRequest, final Locale locale) {
        return findSelectedValueFromQueryString(this, httpRequest)
                .map(option -> option.getLocalizedValue(locale).stream()
                    .map(SortExpression::<T>of)
                    .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    default List<QuerySort<T>> buildQueryExpressions(final Http.Request httpRequest, final Locale locale) {
        return findSelectedValueFromQueryString(this, httpRequest)
                .map(option -> option.getLocalizedValue(locale).stream()
                        .map(QuerySort::<T>of)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    static <T> SortFormSettings<T> of(final String fieldName, final List<SortFormOption> options) {
        return new SortFormSettingsImpl<>(fieldName, options);
    }
}
