package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseModel;

import java.util.List;

final class FacetedSearchFormSettingsListImpl<T> extends SunriseModel implements FacetedSearchFormSettingsList<T> {

    private final SimpleFacetedSearchFormSettingsList simpleSettings;
    private final List<? extends FacetedSearchFormSettings<T>> settings;

    FacetedSearchFormSettingsListImpl(final SimpleFacetedSearchFormSettingsList simpleSettings,
                                      final List<? extends FacetedSearchFormSettings<T>> settings) {
        this.simpleSettings = simpleSettings;
        this.settings = settings;
    }

    @Override
    public List<? extends SimpleFacetedSearchFormSettings> getSimpleSettings() {
        return simpleSettings.getSimpleSettings();
    }

    @Override
    public List<? extends FacetedSearchFormSettings<T>> getSettings() {
        return settings;
    }
}
