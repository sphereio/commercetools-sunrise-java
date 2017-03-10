package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.SunriseModel;

public abstract class AbstractFormSettings<T> extends SunriseModel implements FormSettings<T> {

    private final String fieldName;
    private final T defaultValue;

    protected AbstractFormSettings(final String fieldName, final T defaultValue) {
        this.fieldName = fieldName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }
}

