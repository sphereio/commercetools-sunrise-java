package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.forms.MessageType;
import com.commercetools.sunrise.framework.viewmodels.forms.MessageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class ViewModelFactory {

    protected static List<MessageViewModel> extractMessages(final Map<String, String> map) {
        final List<MessageViewModel> messageViewModels = new ArrayList<>();
        Stream.of(MessageType.values())
                .forEach(type -> findMessage(type, map)
                        .ifPresent(messageViewModels::add));
        return messageViewModels;
    }

    private static Optional<MessageViewModel> findMessage(final MessageType type, final Map<String, String> map) {
        return Optional.ofNullable(map.get(type.name()))
                .map(message -> createMessage(type, message));
    }

    private static MessageViewModel createMessage(final MessageType type, final String message) {
        final MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setMessage(message);
        messageViewModel.setType(type.name().toLowerCase());
        return messageViewModel;
    }
}
