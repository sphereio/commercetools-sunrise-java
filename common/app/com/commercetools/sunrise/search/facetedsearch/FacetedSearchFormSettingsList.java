package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.FacetedSearchExpression;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface FacetedSearchFormSettingsList<T> {

    List<TermFacetedSearchFormSettings<T>> getTermSettings();

    List<RangeFacetedSearchFormSettings<T>> getRangeSettings();

    default List<FacetedSearchExpression<T>> buildSearchExpressions(final Http.Request httpRequest, final Locale locale) {
        final Stream<FacetedSearchExpression<T>> termExpressions = getTermSettings().stream()
                .map(setting -> setting.buildSearchExpression(httpRequest, locale));
        final Stream<FacetedSearchExpression<T>> rangeExpressions = getRangeSettings().stream()
                .map(setting -> setting.buildSearchExpression(httpRequest, locale));
        return Stream.concat(termExpressions, rangeExpressions).collect(toList());
    }
}
