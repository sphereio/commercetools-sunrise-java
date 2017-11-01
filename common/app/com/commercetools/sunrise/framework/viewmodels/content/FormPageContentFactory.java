package com.commercetools.sunrise.framework.viewmodels.content;

import com.commercetools.sunrise.framework.viewmodels.FormViewModelFactory;
import play.data.Form;

import static com.commercetools.sunrise.framework.viewmodels.content.PageContent.*;
import static java.util.Arrays.asList;

public abstract class FormPageContentFactory<M extends PageContent, I, F> extends FormViewModelFactory<M, I, F> {

    @Override
    protected void initialize(final M viewModel, final I input, final Form<? extends F> form) {
        fillTitle(viewModel, input, form);
        fillMessages(viewModel, input, form);
    }

    protected abstract void fillTitle(final M viewModel, final I input, final Form<? extends F> form);

    protected void fillMessages(final M viewModel, final I input, final Form<? extends F> form) {
        viewModel.addMessagesFromFlash(asList(SUCCESS_MSG, WARNING_MSG, INFO_MSG, DANGER_MSG));
    }
}
