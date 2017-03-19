package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseModel;

import javax.annotation.Nullable;
import java.util.Locale;

import static com.commercetools.sunrise.search.SearchUtils.localizeExpression;

public abstract class AbstractFacetedSearchFormSettings<S extends SimpleFacetedSearchFormSettings> extends SunriseModel implements SimpleFacetedSearchFormSettings {

    private final S settings;
    private final Locale locale;

    protected AbstractFacetedSearchFormSettings(final S settings, final Locale locale) {
        this.settings = settings;
        this.locale = locale;
    }

    protected final S getSettings() {
        return settings;
    }

    protected final Locale getLocale() {
        return locale;
    }

    @Override
    public String getFieldLabel() {
        return settings.getFieldLabel();
    }

    @Override
    public String getAttributePath() {
        return localizeExpression(settings.getAttributePath(), locale);
    }

    @Override
    public boolean isCountDisplayed() {
        return settings.isCountDisplayed();
    }

    @Override
    @Nullable
    public String getUIType() {
        return settings.getUIType();
    }
}
