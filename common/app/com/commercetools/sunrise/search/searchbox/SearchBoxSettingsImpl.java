package com.commercetools.sunrise.search.searchbox;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettings;

class SearchBoxSettingsImpl extends AbstractFormSettings<String> implements SearchBoxSettings {

    SearchBoxSettingsImpl(final String fieldName) {
        super(fieldName, "");
    }
}