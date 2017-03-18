package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.BucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.DefaultBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.DefaultSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.DefaultTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public abstract class FacetedSearchFormSettingsListFactory<T> {

    private final SimpleFacetedSearchFormSettingsList<T> settingsList;
    private final Locale locale;

    protected FacetedSearchFormSettingsListFactory(final SimpleFacetedSearchFormSettingsList<T> settingsList, final Locale locale) {
        this.settingsList = settingsList;
        this.locale = locale;
    }

    protected final Locale getLocale() {
        return locale;
    }

    public final FacetedSearchFormSettingsList<T> create() {
        final List<PositionedSettings<TermFacetedSearchFormSettings<T>>> termSettings = settingsList.getSimpleTermSettings().stream()
                .map(positioned -> PositionedSettings.of(positioned.getPosition(), createTermSettings(positioned, locale)))
                .collect(toList());
        final List<PositionedSettings<SliderRangeFacetedSearchFormSettings<T>>> sliderRangeSettings = settingsList.getSimpleSliderRangeSettings().stream()
                .map(positioned -> PositionedSettings.of(positioned.getPosition(), createSliderRangeSettings(positioned, locale)))
                .collect(toList());
        final List<PositionedSettings<BucketRangeFacetedSearchFormSettings<T>>> bucketRangeSettings = settingsList.getSimpleBucketRangeSettings().stream()
                .map(positioned -> PositionedSettings.of(positioned.getPosition(), createBucketRangeSettings(positioned, locale)))
                .collect(toList());
        return new DefaultFacetedSearchFormSettingsList<>(settingsList, termSettings, sliderRangeSettings, bucketRangeSettings);
    }

    protected TermFacetedSearchFormSettings<T> createTermSettings(final PositionedSettings<SimpleTermFacetedSearchFormSettings<T>> positioned, final Locale locale) {
        return new DefaultTermFacetedSearchFormSettings<>(positioned.getSettings(), locale);
    }

    protected SliderRangeFacetedSearchFormSettings<T> createSliderRangeSettings(final PositionedSettings<SimpleSliderRangeFacetedSearchFormSettings<T>> positioned, final Locale locale) {
        return new DefaultSliderRangeFacetedSearchFormSettings<>(positioned.getSettings(), locale);
    }

    protected BucketRangeFacetedSearchFormSettings<T> createBucketRangeSettings(final PositionedSettings<SimpleBucketRangeFacetedSearchFormSettings<T>> positioned, final Locale locale) {
        return new DefaultBucketRangeFacetedSearchFormSettings<>(positioned.getSettings(), locale);
    }
}
