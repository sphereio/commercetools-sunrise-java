package com.commercetools.sunrise.framework.viewmodels.forms;

import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static java.util.stream.Collectors.toList;

public interface FormSettingsWithOptions<T extends FormOption<V>, V> extends WithFormFieldName, WithFormOptions<T, V> {

    @Nullable
    default String getSelectedFieldValue(final Http.Request httpRequest) {
        return findSelectedValueFromQueryString(this, httpRequest)
                .map(FormOption::getFieldValue)
                .orElse(null);
    }

    @Nullable
    default V getSelectedValue(final Http.Request httpRequest) {
        return findSelectedValueFromQueryString(this, httpRequest)
                .map(FormOption::getValue)
                .orElse(null);
    }

    default List<String> getAllSelectedFieldValues(final Http.Request httpRequest) {
        return findAllSelectedValuesFromQueryString(this, httpRequest).stream()
                .map(FormOption::getFieldValue)
                .collect(toList());
    }

    default List<V> getAllSelectedValues(final Http.Request httpRequest) {
        return findAllSelectedValuesFromQueryString(this, httpRequest).stream()
                .map(FormOption::getValue)
                .collect(toList());
    }
}
