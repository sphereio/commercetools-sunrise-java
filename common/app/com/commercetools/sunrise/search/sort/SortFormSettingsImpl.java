package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;

import java.util.List;

class SortFormSettingsImpl extends AbstractFormSettingsWithOptions<SortFormOption> implements SortFormSettings {

    SortFormSettingsImpl(final String fieldName, final List<SortFormOption> options) {
        super(fieldName, options);
    }
}
