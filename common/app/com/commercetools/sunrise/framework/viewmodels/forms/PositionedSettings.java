package com.commercetools.sunrise.framework.viewmodels.forms;

import com.commercetools.sunrise.framework.SunriseModel;

public final class PositionedSettings<T> extends SunriseModel {

    private final int position;
    private final T settings;

    private PositionedSettings(final int position, final T settings) {
        this.position = position;
        this.settings = settings;
    }

    public int getPosition() {
        return position;
    }

    public T getSettings() {
        return settings;
    }

    public static <T> PositionedSettings<T> of(final int position, final T element) {
        return new PositionedSettings<>(position, element);
    }
}
