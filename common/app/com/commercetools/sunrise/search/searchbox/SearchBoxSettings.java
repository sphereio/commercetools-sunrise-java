package com.commercetools.sunrise.search.searchbox;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import io.sphere.sdk.models.LocalizedStringEntry;
import play.mvc.Http;

import java.util.Locale;

public interface SearchBoxSettings extends FormSettings<String> {

    default LocalizedStringEntry getSearchText(final Http.Request httpRequest, final Locale locale) {
        final String selectedValue = this.getSelectedValue(httpRequest);
        return LocalizedStringEntry.of(locale, selectedValue);
    }

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