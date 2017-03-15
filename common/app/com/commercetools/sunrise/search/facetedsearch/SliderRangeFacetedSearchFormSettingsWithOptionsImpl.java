package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;
import java.util.List;

class SliderRangeFacetedSearchFormSettingsWithOptionsImpl<T> extends AbstractFormSettingsWithOptions<BucketRangeFacetedSearchFormOption, String> implements SliderRangeFacetedSearchFormSettingsWithOptions<T> {

    private final SliderRangeFacetedSearchFormSettings<T> settings;

    SliderRangeFacetedSearchFormSettingsWithOptionsImpl(final SliderRangeFacetedSearchFormSettings<T> settings, final List<BucketRangeFacetedSearchFormOption> options) {
        super(settings.getFieldName(), options);
        this.settings = settings;
    }

    @Override
    public String getLabel() {
        return settings.getLabel();
    }

    @Override
    public String getAttributePath() {
        return settings.getAttributePath();
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
