package com.commercetools.sunrise.search.searchbox;

import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettings;
import play.Configuration;

public class SearchBoxSettings extends AbstractFormSettings<String> {

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "q";

    protected SearchBoxSettings(final Configuration configuration) {
        super(configuration.getString(CONFIG_KEY, DEFAULT_KEY), "");
    }

    @Override
    public String mapToValue(final String valueAsString) {
        return valueAsString;
    }

    @Override
    public boolean isValidValue(final String value) {
        return value != null && !value.isEmpty();
    }

    public static SearchBoxSettings of(final Configuration configuration) {
        return new SearchBoxSettings(configuration);
    }
}