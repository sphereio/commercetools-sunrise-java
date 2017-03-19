package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.WithFormFieldName;
import com.commercetools.sunrise.framework.viewmodels.forms.WithFormOptions;

import java.util.List;

public interface SimpleSortFormSettings extends WithFormFieldName, WithFormOptions<SortFormOption, List<String>> {

    static SimpleSortFormSettings of(final String fieldName, final List<SortFormOption> options) {
        return new SimpleSortFormSettingsImpl(fieldName, options);
    }
}
