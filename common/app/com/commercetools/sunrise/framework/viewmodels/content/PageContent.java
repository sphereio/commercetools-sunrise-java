package com.commercetools.sunrise.framework.viewmodels.content;

import com.commercetools.sunrise.framework.components.viewmodels.ViewModelComponent;
import com.commercetools.sunrise.framework.viewmodels.ViewModel;
import com.commercetools.sunrise.framework.viewmodels.forms.MessageViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static play.mvc.Controller.flash;

public abstract class PageContent extends ViewModel {

    public static final String SUCCESS_MSG = "success";
    public static final String WARNING_MSG = "warning";
    public static final String INFO_MSG = "info";
    public static final String DANGER_MSG = "danger";

    private String title;
    private List<MessageViewModel> messages;
    private List<ViewModelComponent> components;

    public PageContent() {
    }

    public final String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<MessageViewModel> getMessages() {
        return messages;
    }

    public void setMessages(final List<MessageViewModel> messages) {
        this.messages = messages;
    }

    public void addMessage(final MessageViewModel message) {
        if (this.messages == null) {
            this.messages = new LinkedList<>();
        }
        this.messages.add(message);
    }

    public void addMessagesFromFlash(final List<String> messageKeys) {
        if (!messageKeys.isEmpty()) {
            messageKeys.forEach(key -> findMessage(key).ifPresent(this::addMessage));
        }
    }

    public List<ViewModelComponent> getComponents() {
        return components;
    }

    public void setComponents(final List<ViewModelComponent> components) {
        this.components = components;
    }

    public void addComponent(final ViewModelComponent component) {
        if (this.components == null) {
            this.components = new LinkedList<>();
        }
        this.components.add(component);
    }

    private Optional<MessageViewModel> findMessage(final String key) {
        return Optional.ofNullable(flash(key))
                .map(message -> createMessage(key, message));
    }

    private MessageViewModel createMessage(final String key, final String message) {
        final MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setMessage(message);
        messageViewModel.setType(key);
        return messageViewModel;
    }
}
