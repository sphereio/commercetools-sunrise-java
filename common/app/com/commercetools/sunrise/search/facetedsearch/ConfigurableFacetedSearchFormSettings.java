package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import javax.annotation.Nullable;
import java.util.Optional;

abstract class ConfigurableFacetedSearchFormSettings<T> extends FacetedSearchFormSettingsImpl<T> {

    private static final String CONFIG_FIELD_NAME = "fieldName";
    private static final String CONFIG_UI_TYPE = "uiType";
    private static final String CONFIG_LABEL = "label";
    private static final String CONFIG_ATTRIBUTE_PATH = "attributePath";
    private static final String CONFIG_COUNT = "count";

    ConfigurableFacetedSearchFormSettings(final Configuration configuration, final int position) {
        super(key(configuration), label(configuration), attributePath(configuration), position,
                isCountDisplayed(configuration), uiType(configuration));
    }

    private static String key(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_FIELD_NAME))
                .orElseThrow(() -> new SunriseConfigurationException("Missing required field name for facet", CONFIG_FIELD_NAME, configuration));
    }

    private static String label(final Configuration configuration) {
        return configuration.getString(CONFIG_LABEL, "");
    }

    private static String attributePath(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_ATTRIBUTE_PATH))
                .orElseThrow(() -> new SunriseConfigurationException("Missing facet attribute path expression", CONFIG_ATTRIBUTE_PATH, configuration));
    }

    private static Boolean isCountDisplayed(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_COUNT, true);
    }

    @Nullable
    private static String uiType(final Configuration configuration) {
        return configuration.getString(CONFIG_UI_TYPE);
    }
}
