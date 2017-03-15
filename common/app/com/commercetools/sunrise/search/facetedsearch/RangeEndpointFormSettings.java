package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;

public interface RangeEndpointFormSettings extends FormSettings<String> {

    @Override
    default String getDefaultValue() {
        return "*";
    }

    @Override
    default String mapToValue(final String valueAsString) {
        return "\"" + valueAsString + "\"";
    }

    @Override
    default boolean isValidValue(final String value) {
        return true;
    }

    /**
     * Generates the facet options according to the facet result provided.
     * @return the generated facet options
     */
    static RangeEndpointFormSettings of(final String fieldName) {
        return new RangeEndpointFormSettingsImpl(fieldName);
    }
}
