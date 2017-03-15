package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.FacetedSearchExpression;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public interface FacetedSearchFormSettingsList<T> {

    List<TermFacetedSearchFormSettings<T>> getTermSettings();

    List<SliderRangeFacetedSearchFormSettings<T>> getSliderRangeSettings();

    List<BucketRangeFacetedSearchFormSettings<T>> getBucketRangeSettings();

    default List<FacetedSearchExpression<T>> buildSearchExpressions(final Http.Request httpRequest, final Locale locale) {
        return concat(getTermSettings().stream(), concat(getSliderRangeSettings().stream(), getBucketRangeSettings().stream()))
                .map(setting -> setting.buildSearchExpression(httpRequest, locale))
                .collect(toList());
    }
}
