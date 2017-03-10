package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;

import java.util.List;

public interface EntriesPerPageFormSettings extends FormSettingsWithOptions<EntriesPerPageFormOption> {

    static EntriesPerPageFormSettings of(final String fieldName, final List<EntriesPerPageFormOption> options) {
        return new EntriesPerPageFormSettingsImpl(fieldName, options);
    }
}
