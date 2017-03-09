package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.SunriseModel;
import play.data.Form;

import java.util.List;

public class FormFieldWithOptions<T> extends SunriseModel {

    private final Form.Field formField;
    private final List<T> formOptions;

    public FormFieldWithOptions(final Form.Field formField, final List<T> formOptions) {
        this.formField = formField;
        this.formOptions = formOptions;
    }

    public Form.Field getFormField() {
        return formField;
    }

    public List<T> getFormOptions() {
        return formOptions;
    }
}
