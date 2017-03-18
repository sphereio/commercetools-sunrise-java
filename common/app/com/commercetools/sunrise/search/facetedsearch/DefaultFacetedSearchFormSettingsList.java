package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;

import java.util.List;

public final class DefaultFacetedSearchFormSettingsList<T> extends SunriseModel implements FacetedSearchFormSettingsList<T> {

    private final SimpleFacetedSearchFormSettingsList<T> settingsList;
    private final List<PositionedSettings<TermFacetedSearchFormSettings<T>>> termSettings;
    private final List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> sliderRangeSettings;
    private final List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> bucketRangeSettings;

    public DefaultFacetedSearchFormSettingsList(final SimpleFacetedSearchFormSettingsList<T> settingsList,
                                                final List<PositionedSettings<TermFacetedSearchFormSettings<T>>> termSettings,
                                                final List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> sliderRangeSettings,
                                                final List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> bucketRangeSettings) {
        this.settingsList = settingsList;
        this.termSettings = termSettings;
        this.sliderRangeSettings = sliderRangeSettings;
        this.bucketRangeSettings = bucketRangeSettings;
    }

    @Override
    public List<PositionedSettings<SimpleTermFacetedSearchFormSettings<T>>> getSimpleTermSettings() {
        return settingsList.getSimpleTermSettings();
    }

    @Override
    public List<PositionedSettings<SimpleSliderRangeFacetedSearchFormSettings<T>>> getSimpleSliderRangeSettings() {
        return settingsList.getSimpleSliderRangeSettings();
    }

    @Override
    public List<PositionedSettings<SimpleBucketRangeFacetedSearchFormSettings<T>>> getSimpleBucketRangeSettings() {
        return settingsList.getSimpleBucketRangeSettings();
    }

    @Override
    public List<PositionedSettings<TermFacetedSearchFormSettings<T>>> getTermSettings() {
        return termSettings;
    }

    @Override
    public List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> getSliderRangeSettings() {
        return sliderRangeSettings;
    }

    @Override
    public List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> getBucketRangeSettings() {
        return bucketRangeSettings;
    }
}
