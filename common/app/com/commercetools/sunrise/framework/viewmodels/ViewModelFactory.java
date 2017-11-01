package com.commercetools.sunrise.framework.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.forms.MessageViewModel;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class ViewModelFactory {

    public static final String SUCCESS_MSG = "success";
    public static final String WARNING_MSG = "warning";
    public static final String INFO_MSG = "info";
    public static final String DANGER_MSG = "danger";

    protected static List<MessageViewModel> extractMessages(final Http.Flash flash) {
        final List<MessageViewModel> messageViewModels = new ArrayList<>();
        Stream.of(SUCCESS_MSG, WARNING_MSG, INFO_MSG, DANGER_MSG)
                .forEach(key -> findMessage(key, flash)
                        .ifPresent(messageViewModels::add));
        return messageViewModels;
    }

    private static Optional<MessageViewModel> findMessage(final String key, final Http.Flash flash) {
        return Optional.ofNullable(flash.get(key))
                .map(message -> createMessage(key, message));
    }

    private static MessageViewModel createMessage(final String key, final String message) {
        final MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setMessage(message);
        messageViewModel.setType(key);
        return messageViewModel;
    }
}
