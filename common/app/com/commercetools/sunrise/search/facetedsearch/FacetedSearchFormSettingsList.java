package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import io.sphere.sdk.search.FacetedSearchExpression;
import play.mvc.Http;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public interface FacetedSearchFormSettingsList<T> extends SimpleFacetedSearchFormSettingsList<T> {

    List<PositionedSettings<TermFacetedSearchFormSettings<T>>> getTermSettings();

    List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> getSliderRangeSettings();

    List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> getBucketRangeSettings();

    default List<FacetedSearchExpression<T>> buildFacetedSearchExpressions(final Http.Context httpContext) {
        return concat(getTermSettings().stream(), concat(getSliderRangeSettings().stream(), getBucketRangeSettings().stream()))
                .map(positioned -> positioned.getSettings().buildFacetedSearchExpression(httpContext))
                .collect(toList());
    }

    default List<? extends FacetedSearchFormSettings<T>> getAllSettingsSorted() {
        return concat(getTermSettings().stream(), concat(getSliderRangeSettings().stream(), getBucketRangeSettings().stream()))
                .sorted(Comparator.comparingDouble(PositionedSettings::getPosition))
                .map(PositionedSettings::getSettings)
                .collect(toList());
    }
}
