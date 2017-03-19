package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;

import javax.annotation.Nullable;
import java.util.Locale;

public class DefaultTermFacetedSearchFormSettings<T> extends AbstractFacetedSearchFormSettingsWithOptions<SimpleTermFacetedSearchFormSettings> implements TermFacetedSearchFormSettings<T> {

    public DefaultTermFacetedSearchFormSettings(final SimpleTermFacetedSearchFormSettings settings, final Locale locale) {
        super(settings, locale);
    }

    @Override
    public String getFieldName() {
        return getSettings().getFieldName();
    }

    @Nullable
    @Override
    public Long getThreshold() {
        return getSettings().getThreshold();
    }

    @Nullable
    @Override
    public Long getLimit() {
        return getSettings().getLimit();
    }

    @Nullable
    @Override
    public TermFacetMapperSettings getMapperSettings() {
        return getSettings().getMapperSettings();
    }
}
