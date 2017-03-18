package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import play.mvc.Http;

import javax.annotation.Nullable;

public interface PaginationSettings extends FormSettings<Integer> {

    int getDisplayedPages();

    default long getOffset(final Http.Context httpContext, final long limit) {
        return (getSelectedValue(httpContext) - 1) * limit;
    }

    @Nullable
    @Override
    default Integer mapFieldValueToValue(final String fieldValue) {
        try {
            return Integer.valueOf(fieldValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    default boolean isValidValue(@Nullable final Integer value) {
        return value != null && value > 0;
    }

    static PaginationSettings of(final String fieldName, final int displayedPages) {
        return new PaginationSettingsImpl(fieldName, displayedPages);
    }
}