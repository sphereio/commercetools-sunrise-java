package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;
import java.util.List;

class TermFacetedSearchFormSettingsWithOptionsImpl<T> extends AbstractFormSettingsWithOptions<TermFacetedSearchFormOption, String> implements TermFacetedSearchFormSettingsWithOptions<T> {

    private final TermFacetedSearchFormSettings<T> settings;

    TermFacetedSearchFormSettingsWithOptionsImpl(final TermFacetedSearchFormSettings<T> settings, final List<TermFacetedSearchFormOption> options) {
        super(settings.getFieldName(), options);
        this.settings = settings;
    }

    @Override
    public String getLabel() {
        return settings.getLabel();
    }

    @Override
    @Nullable
    public Long getThreshold() {
        return settings.getThreshold();
    }

    @Override
    public String getAttributePath() {
        return settings.getAttributePath();
    }

    @Override
    @Nullable
    public Long getLimit() {
        return settings.getLimit();
    }

    @Override
    public int getPosition() {
        return settings.getPosition();
    }

    @Override
    @Nullable
    public FacetUIType getUIType() {
        return settings.getUIType();
    }

    @Override
    public boolean isCountDisplayed() {
        return settings.isCountDisplayed();
    }

    @Override
    public boolean isMultiSelect() {
        return settings.isMultiSelect();
    }

    @Override
    public boolean isMatchingAll() {
        return settings.isMatchingAll();
    }

    @Override
    @Nullable
    public FacetMapperSettings getMapperSettings() {
        return settings.getMapperSettings();
    }
}
