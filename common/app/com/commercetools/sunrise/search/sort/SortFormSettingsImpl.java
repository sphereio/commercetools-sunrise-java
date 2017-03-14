package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;

import java.util.List;

class SortFormSettingsImpl<T> extends AbstractFormSettingsWithOptions<SortFormOption, List<String>> implements SortFormSettings<T> {

    SortFormSettingsImpl(final String fieldName, final List<SortFormOption> options) {
        super(fieldName, options);
    }
}
