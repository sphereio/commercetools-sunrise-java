package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

public interface SimpleFacetedSearchFormSettingsList {

    List<? extends SimpleFacetedSearchFormSettings> getSimpleSettings();

    static SimpleFacetedSearchFormSettingsList of(final List<? extends SimpleFacetedSearchFormSettings> simpleSettings) {
        return new SimpleFacetedSearchFormSettingsListImpl(simpleSettings);
    }
}
