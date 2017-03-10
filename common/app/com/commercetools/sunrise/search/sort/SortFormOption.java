package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.viewmodels.forms.FormOption;

import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.search.SearchUtils.localizeExpression;
import static java.util.stream.Collectors.toList;

/**
 * An option to sort the results from a query or search on some resource endpoint.
 */
public interface SortFormOption extends FormOption<List<String>> {

    /**
     * Gets the sort expressions associated with this option, representing the attribute path and sorting direction.
     * The expressions might contain {@code {{locale}}}, which should be replaced with the current locale before using them.
     * @return the sort expression for this option
     */
    @Override
    List<String> getValue();

    /**
     * Gets the localized expressions associated with this option, representing the attribute path and sorting direction.
     * These expressions are ready to be sent to the CTP platform.
     * @param locale the current user's locale
     * @return the localized sort expression for this option
     */
    default List<String> getLocalizedValue(final Locale locale) {
        return getValue().stream()
                .map(expr -> localizeExpression(expr, locale))
                .collect(toList());
    }

    static SortFormOption of(final String fieldLabel, final String fieldValue, final List<String> expressions, final boolean isDefault) {
        return new SortFormOptionImpl(fieldLabel, fieldValue, expressions, isDefault);
    }
}
