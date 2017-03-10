package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;

public interface PaginationSettings extends FormSettings<Integer> {

    int getDisplayedPages();

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