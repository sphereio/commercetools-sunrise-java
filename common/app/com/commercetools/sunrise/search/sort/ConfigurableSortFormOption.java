package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ConfigurableSortFormOption extends SortFormOptionImpl {

    private static final String CONFIG_FIELD_VALUE = "fieldValue";
    private static final String CONFIG_FIELD_LABEL = "fieldLabel";
    private static final String CONFIG_EXPRESSIONS = "expressions";
    private static final String CONFIG_DEFAULT = "default";

    public ConfigurableSortFormOption(final Configuration configuration) {
        super(fieldLabel(configuration), fieldValue(configuration), expressions(configuration), isDefault(configuration));
    }

    private static String fieldLabel(final Configuration configuration) {
        return configuration.getString(CONFIG_FIELD_LABEL, "");
    }

    private static String fieldValue(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_FIELD_VALUE))
                .orElseThrow(() -> new SunriseConfigurationException("Missing sort field value", CONFIG_FIELD_VALUE, configuration));
    }

    private static List<String> expressions(final Configuration configuration) {
        return configuration.getStringList(CONFIG_EXPRESSIONS, emptyList()).stream()
                .filter(expr -> !expr.isEmpty())
                .collect(toList());
    }

    private static Boolean isDefault(final Configuration configuration) {
        return configuration.getBoolean(CONFIG_DEFAULT, false);
    }
}
