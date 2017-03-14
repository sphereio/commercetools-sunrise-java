package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;

public interface EntriesPerPageFormSettings extends FormSettingsWithOptions<EntriesPerPageFormOption, Integer> {

    int DEFAULT_LIMIT = 20;

    default long getLimit(final Http.Request httpRequest) {
        return Optional.ofNullable(getSelectedValue(httpRequest)).orElse(DEFAULT_LIMIT);
    }

    static EntriesPerPageFormSettings of(final String fieldName, final List<EntriesPerPageFormOption> options) {
        return new EntriesPerPageFormSettingsImpl(fieldName, options);
    }
}
