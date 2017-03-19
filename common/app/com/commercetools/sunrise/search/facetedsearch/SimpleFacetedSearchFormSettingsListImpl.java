package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseModel;

import java.util.List;

final class SimpleFacetedSearchFormSettingsListImpl extends SunriseModel implements SimpleFacetedSearchFormSettingsList {

    private final List<? extends SimpleFacetedSearchFormSettings> simpleSettings;

    SimpleFacetedSearchFormSettingsListImpl(final List<? extends SimpleFacetedSearchFormSettings> simpleSettings) {
        this.simpleSettings = simpleSettings;
    }

    @Override
    public List<? extends SimpleFacetedSearchFormSettings> getSimpleSettings() {
        return simpleSettings;
    }
}
