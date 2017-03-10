package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;

import java.util.List;

public interface SortFormSettings extends FormSettingsWithOptions<SortFormOption> {

    static SortFormSettings of(final String fieldName, final List<SortFormOption> options) {
        return new SortFormSettingsImpl(fieldName, options);
    }
}
