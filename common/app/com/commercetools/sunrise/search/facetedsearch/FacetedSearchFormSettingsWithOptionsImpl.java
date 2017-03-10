package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import io.sphere.sdk.facets.TermFacetsMapper2;
import io.sphere.sdk.facets.FacetType;

import javax.annotation.Nullable;
import java.util.List;

class FacetedSearchFormSettingsWithOptionsImpl<T extends FacetedSearchFormOption> extends AbstractFormSettingsWithOptions<T> implements FacetedSearchFormSettingsWithOptions<T> {

    private final FacetedSearchFormSettings settings;

    FacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<T> options) {
        super(settings.getFieldName(), options);
        this.settings = settings;
    }

    @Override
    public FacetType getType() {
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
    public TermFacetsMapper2 getMapper() {
        return settings.getMapper();
    }

}
