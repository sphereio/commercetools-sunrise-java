package com.commercetools.sunrise.framework.viewmodels.content;

import com.commercetools.sunrise.framework.template.i18n.I18nIdentifierResolver;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.commercetools.sunrise.framework.viewmodels.forms.MessageViewModel;
import com.commercetools.sunrise.framework.viewmodels.forms.MessagesViewModel;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static play.mvc.Controller.flash;

public abstract class PageContentFactory<M extends PageContent, I> extends SimpleViewModelFactory<M, I> {

    public static final String SUCCESS = "success";
    public static final String WARNING = "warning";
    public static final String INFO = "info";
    public static final String ERROR = "error";

    protected I18nIdentifierResolver i18nIdentifierResolver;

    protected PageContentFactory(final I18nIdentifierResolver i18nIdentifierResolver) {
        this.i18nIdentifierResolver = i18nIdentifierResolver;
    }

    protected final I18nIdentifierResolver getI18nIdentifierResolver() {
        return i18nIdentifierResolver;
    }

    @Override
    protected void initialize(final M viewModel, final I input) {
        fillTitle(viewModel, input);
        fillMessages(viewModel, input);
    }

    private void fillMessages(final M viewModel, final I input) {
        viewModel.setMessages(extractMessages(asList(SUCCESS, WARNING, INFO, ERROR)));
    }

    protected abstract void fillTitle(final M viewModel, final I input);

    protected final MessagesViewModel extractMessages(final List<String> keys) {
        final MessagesViewModel messagesViewModel = new MessagesViewModel();
        keys.forEach(key -> findMessage(key).ifPresent(messagesViewModel::addMessage));
        return messagesViewModel;
    }

    private Optional<MessageViewModel> findMessage(final String key) {
        return Optional.ofNullable(flash(key))
                .map(message -> createMessage(key, message));
    }

    private MessageViewModel createMessage(final String key, final String message) {
        final MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setMessage(i18nIdentifierResolver.resolveOrKey(message));
        messageViewModel.setType(key);
        return messageViewModel;
    }
}
