package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;

import javax.annotation.Nullable;
import java.util.List;

abstract class FacetedSearchFormSettingsWithOptionsImpl<T extends FacetedSearchFormOption> extends AbstractFormSettingsWithOptions<T, String> implements FacetedSearchFormSettingsWithOptions<T> {

    private final FacetedSearchFormSettings settings;

    FacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<T> options) {
        super(settings.getFieldName(), options);
        this.settings = settings;
    }

    @Override
    public String getLabel() {
        return settings.getLabel();
    }

    @Override
    public String getExpression() {
        return settings.getExpression();
    }

    @Override
    public FacetUIType getType() {
        return settings.getType();
    }

    @Override
    @Nullable
    public Long getThreshold() {
        return settings.getThreshold();
    }

    @Override
    @Nullable
    public Long getLimit() {
        return settings.getLimit();
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
    public FacetMapperSettings getMapper() {
        return settings.getMapper();
    }

}
