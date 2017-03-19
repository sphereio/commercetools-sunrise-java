package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;

import java.util.List;

class SimpleSortFormSettingsImpl extends AbstractFormSettingsWithOptions<SortFormOption, List<String>> implements SimpleSortFormSettings {

    SimpleSortFormSettingsImpl(final String fieldName, final List<SortFormOption> options) {
        super(fieldName, options);
    }
}
