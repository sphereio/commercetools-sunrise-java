package com.commercetools.sunrise.framework.viewmodels.content;

import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;

import static com.commercetools.sunrise.framework.viewmodels.content.PageContent.*;
import static java.util.Arrays.asList;

public abstract class PageContentFactory<M extends PageContent, I> extends SimpleViewModelFactory<M, I> {

    @Override
    protected void initialize(final M viewModel, final I input) {
        fillTitle(viewModel, input);
        fillMessages(viewModel, input);
    }

    protected abstract void fillTitle(final M viewModel, final I input);

    protected void fillMessages(final M viewModel, final I input) {
        viewModel.addMessagesFromFlash(asList(SUCCESS_MSG, WARNING_MSG, INFO_MSG, DANGER_MSG));
    }
}
