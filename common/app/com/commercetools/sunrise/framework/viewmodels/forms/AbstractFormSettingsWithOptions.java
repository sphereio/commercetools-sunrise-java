package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.SunriseModel;

import java.util.List;

public abstract class AbstractFormSettingsWithOptions<T extends FormOption> extends SunriseModel implements FormSettingsWithOptions<T> {

    private final String fieldName;
    private final List<T> options;

    protected AbstractFormSettingsWithOptions(final String fieldName, final List<T> options) {
        this.fieldName = fieldName;
        this.options = options;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public List<T> getOptions() {
        return options;
    }
}
