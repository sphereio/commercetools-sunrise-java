package com.commercetools.sunrise.framework.viewmodels.forms;

import play.mvc.Http;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;

public interface FormSettings<T> extends WithFormFieldName {

    T getDefaultValue();

    T mapToValue(final String valueAsString);

    boolean isValidValue(final T value);

    default T getSelectedValue(final Http.Request httpRequest) {
        return findSelectedValueFromQueryString(this, httpRequest);
    }
}

