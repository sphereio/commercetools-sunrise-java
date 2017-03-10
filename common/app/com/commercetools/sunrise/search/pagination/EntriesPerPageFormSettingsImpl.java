package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import play.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

class EntriesPerPageFormSettingsImpl extends AbstractFormSettingsWithOptions<EntriesPerPageFormOption> implements EntriesPerPageFormSettings {

    EntriesPerPageFormSettingsImpl(final String fieldName, final List<EntriesPerPageFormOption> options) {
        super(fieldName, options);
    }
}
