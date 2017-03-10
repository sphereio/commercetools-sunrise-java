package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

final class ConfigurableSortFormOption extends SortFormOptionImpl {

    private static final String OPTION_LABEL_ATTR = "label";
    private static final String OPTION_VALUE_ATTR = "value";
    private static final String OPTION_EXPR_ATTR = "expr";
    private static final String OPTION_DEFAULT_ATTR = "default";

    ConfigurableSortFormOption(final Configuration configuration) {
        super(fieldLabel(configuration), fieldValue(configuration), expressions(configuration), isDefault(configuration));
    }

    private static String fieldLabel(final Configuration configuration) {
        return configuration.getString(OPTION_LABEL_ATTR, "");
    }

    private static String fieldValue(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing sort value", OPTION_VALUE_ATTR, configuration));
    }

    private static List<String> expressions(final Configuration configuration) {
        return configuration.getStringList(OPTION_EXPR_ATTR, emptyList()).stream()
                .filter(expr -> !expr.isEmpty())
                .collect(toList());
    }

    private static Boolean isDefault(final Configuration configuration) {
        return configuration.getBoolean(OPTION_DEFAULT_ATTR, false);
    }
}
