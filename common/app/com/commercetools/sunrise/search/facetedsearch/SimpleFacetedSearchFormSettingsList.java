package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public interface SimpleFacetedSearchFormSettingsList<T> {

    List<PositionedSettings<SimpleTermFacetedSearchFormSettings<T>>> getSimpleTermSettings();

    List<PositionedSettings<SimpleSliderRangeFacetedSearchFormSettings<T>>> getSimpleSliderRangeSettings();

    List<PositionedSettings<SimpleBucketRangeFacetedSearchFormSettings<T>>> getSimpleBucketRangeSettings();

    default List<? extends SimpleFacetedSearchFormSettings<T>> getAllSimpleSettingsSorted() {
        return concat(getSimpleTermSettings().stream(), concat(getSimpleSliderRangeSettings().stream(), getSimpleBucketRangeSettings().stream()))
                .sorted(Comparator.comparingDouble(PositionedSettings::getPosition))
                .map(PositionedSettings::getSettings)
                .collect(toList());
    }
}
