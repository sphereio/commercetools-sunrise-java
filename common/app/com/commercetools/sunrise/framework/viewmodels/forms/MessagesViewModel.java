package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

import java.util.List;

public class MessagesViewModel extends ViewModel {

    private List<MessageViewModel> messages;

    public MessagesViewModel() {
    }

    public List<MessageViewModel> getMessages() {
        return messages;
    }

    public void setMessages(final List<MessageViewModel> messages) {
        this.messages = messages;
    }

    public void addMessage(final MessageViewModel message) {
        this.messages.add(message);
    }
}
