package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;
import com.commercetools.sunrise.framework.viewmodels.ViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.FormFieldWithOptions;
import play.data.Form;

import java.util.List;

public abstract class FormFieldViewModelFactory<M extends ViewModel, I> extends ViewModelFactory<M, FormFieldWithOptions<I>> {

    public M createWithDefaultOptions(final Form.Field formField) {
        return create(new FormFieldWithOptions<>(formField, defaultOptions()));
    }

    protected abstract List<I> defaultOptions();
}
