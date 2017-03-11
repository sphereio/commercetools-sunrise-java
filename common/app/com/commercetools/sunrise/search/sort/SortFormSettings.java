package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static java.util.Collections.emptyList;

public interface SortFormSettings extends FormSettingsWithOptions<SortFormOption, List<String>> {

    default List<String> getSelectedLocalizedValue(final Http.Request httpRequest, final Locale locale) {
        return findSelectedValueFromQueryString(this, httpRequest)
                .map(option -> option.getLocalizedValue(locale))
                .orElse(emptyList());
    }

    static SortFormSettings of(final String fieldName, final List<SortFormOption> options) {
        return new SortFormSettingsImpl(fieldName, options);
    }
}
