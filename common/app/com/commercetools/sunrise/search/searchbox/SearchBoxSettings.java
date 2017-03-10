package com.commercetools.sunrise.search.searchbox;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;

public interface SearchBoxSettings extends FormSettings<String> {

    @Override
    default String mapToValue(final String valueAsString) {
        return valueAsString;
    }

    @Override
    default boolean isValidValue(final String value) {
        return value != null && !value.isEmpty();
    }

    static SearchBoxSettings of(final String fieldName) {
        return new SearchBoxSettingsImpl(fieldName);
    }
}