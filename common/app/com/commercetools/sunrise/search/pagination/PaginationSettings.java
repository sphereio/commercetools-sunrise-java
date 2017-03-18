package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import play.mvc.Http;

public interface PaginationSettings extends FormSettings<Integer> {

    int getDisplayedPages();

    default long getOffset(final Http.Context httpContext, final long limit) {
        return (getSelectedValue(httpContext) - 1) * limit;
    }

    @Override
    default Integer mapToValue(final String valueAsString) {
        try {
            return Integer.valueOf(valueAsString);
        } catch (NumberFormatException e) {
            return getDefaultValue();
        }
    }

    @Override
    default boolean isValidValue(final Integer value) {
        return value != null && value > 0;
    }

    static PaginationSettings of(final String fieldName, final int displayedPages) {
        return new PaginationSettingsImpl(fieldName, displayedPages);
    }
}