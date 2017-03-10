package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormOption;

import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toList;

public class FacetedSearchFormOption<T> extends AbstractFormOption<List<T>> {

    private final BiFunction<T, Locale, T> localizationMapper;

    protected FacetedSearchFormOption(final String fieldLabel, final String fieldValue, final List<T> expressions,
                                      final boolean isDefault, final BiFunction<T, Locale, T> localizationMapper) {
        super(fieldLabel, fieldValue, expressions, isDefault);
        this.localizationMapper = localizationMapper;
    }

    @Override
    public String getFieldLabel() {
        return super.getFieldLabel();
    }

    @Override
    public String getFieldValue() {
        return super.getFieldValue();
    }

    /**
     * Gets the sort model associated with this option, representing the attribute path and sorting direction.
     * The expressions might contain {@code {{locale}}}, which should be replaced with the current locale before using them.
     * @return the sort model for this option
     */
    @Override
    public List<T> getValue() {
        return super.getValue();
    }

    @Override
    public boolean isDefault() {
        return super.isDefault();
    }

    public long getCount() {

    }

    public List<T> getLocalizedValue(final Locale locale) {
        return getValue().stream()
                .map(expr -> localizationMapper.apply(expr, locale))
                .collect(toList());
    }

    public static <T> FacetedSearchFormOption<T> of(final String fieldLabel, final String fieldValue, final List<T> expressions,
                                                    final boolean isDefault, final BiFunction<T, Locale, T> localizationMapper) {
        return new FacetedSearchFormOption<>(fieldLabel, fieldValue, expressions, isDefault, localizationMapper);
    }
}
