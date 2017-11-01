package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.forms.MessageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class ViewModelFactory {

    public static final String SUCCESS_MSG = "success";
    public static final String WARNING_MSG = "warning";
    public static final String INFO_MSG = "info";
    public static final String DANGER_MSG = "danger";

    protected static List<MessageViewModel> extractMessages(final Map<String, String> map) {
        final List<MessageViewModel> messageViewModels = new ArrayList<>();
        Stream.of(SUCCESS_MSG, WARNING_MSG, INFO_MSG, DANGER_MSG)
                .forEach(key -> findMessage(key, map)
                        .ifPresent(messageViewModels::add));
        return messageViewModels;
    }

    private static Optional<MessageViewModel> findMessage(final String key, final Map<String, String> map) {
        return Optional.ofNullable(map.get(key))
                .map(message -> createMessage(key, message));
    }

    private static MessageViewModel createMessage(final String key, final String message) {
        final MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setMessage(message);
        messageViewModel.setType(key);
        return messageViewModel;
    }
}
