package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.bucketranges.DefaultBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.DefaultSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.DefaultTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class FacetedSearchFormSettingsListFactory<T> {

    private final SimpleFacetedSearchFormSettingsList simpleSettings;
    private final Locale locale;

    protected FacetedSearchFormSettingsListFactory(final SimpleFacetedSearchFormSettingsList simpleSettings, final Locale locale) {
        this.simpleSettings = simpleSettings;
        this.locale = locale;
    }

    protected final Locale getLocale() {
        return locale;
    }

    public FacetedSearchFormSettingsList<T> create() {
        final List<FacetedSearchFormSettings<T>> list = new ArrayList<>();
        simpleSettings.getSimpleSettings().forEach(settings -> addSettingsToList(list, settings));
        return new FacetedSearchFormSettingsListImpl<>(simpleSettings, list);
    }

    protected void addSettingsToList(final List<FacetedSearchFormSettings<T>> list, final SimpleFacetedSearchFormSettings settings) {
        if (settings instanceof SimpleTermFacetedSearchFormSettings) {
            list.add(createTermSettings((SimpleTermFacetedSearchFormSettings) settings));
        } else if (settings instanceof SimpleSliderRangeFacetedSearchFormSettings) {
            list.add(createSliderRangeSettings((SimpleSliderRangeFacetedSearchFormSettings) settings));
        } else if (settings instanceof SimpleBucketRangeFacetedSearchFormSettings) {
            list.add(createBucketRangeSettings((SimpleBucketRangeFacetedSearchFormSettings) settings));
        }
    }

    protected FacetedSearchFormSettings<T> createTermSettings(final SimpleTermFacetedSearchFormSettings settings) {
        return new DefaultTermFacetedSearchFormSettings<>(settings, locale);
    }

    protected FacetedSearchFormSettings<T> createSliderRangeSettings(final SimpleSliderRangeFacetedSearchFormSettings settings) {
        return new DefaultSliderRangeFacetedSearchFormSettings<>(settings, locale);
    }

    protected FacetedSearchFormSettings<T> createBucketRangeSettings(final SimpleBucketRangeFacetedSearchFormSettings settings) {
        return new DefaultBucketRangeFacetedSearchFormSettings<>(settings, locale);
    }
}
