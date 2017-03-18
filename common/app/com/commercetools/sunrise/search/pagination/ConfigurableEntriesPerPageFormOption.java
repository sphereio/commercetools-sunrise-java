package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import java.util.Optional;

public class ConfigurableEntriesPerPageFormOption extends EntriesPerPageFormOptionImpl {

    private static final String OPTION_FIELD_LABEL_ATTR = "fieldLabel";
    private static final String OPTION_FIELD_VALUE_ATTR = "fieldValue";
    private static final String OPTION_AMOUNT_ATTR = "amount";
    private static final String OPTION_DEFAULT_ATTR = "default";

    private static final int MIN_PAGE_SIZE = 0;
    private static final int MAX_PAGE_SIZE = 500;

    public ConfigurableEntriesPerPageFormOption(final Configuration configuration) {
        super(fieldLabel(configuration), fieldValue(configuration), amount(configuration), isDefault(configuration));
    }

    private static String fieldLabel(final Configuration configuration) {
        return configuration.getString(OPTION_FIELD_LABEL_ATTR, "");
    }

    private static String fieldValue(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(OPTION_FIELD_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page value", OPTION_FIELD_VALUE_ATTR, configuration));
    }

    private static int amount(final Configuration configuration) {
        final int amount = Optional.ofNullable(configuration.getInt(OPTION_AMOUNT_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page amount", OPTION_AMOUNT_ATTR, configuration));
        if (!isValidAmount(amount)) {
            throw new SunriseConfigurationException(String.format("Elements per page option is not within bounds [%d, %d]: %s",
                    MIN_PAGE_SIZE, MAX_PAGE_SIZE, amount), OPTION_AMOUNT_ATTR, configuration);
        }
        return amount;
    }

    private static Boolean isDefault(final Configuration configuration) {
        return configuration.getBoolean(OPTION_DEFAULT_ATTR, false);
    }

    private static boolean isValidAmount(final int amount) {
        return amount >= MIN_PAGE_SIZE && amount <= MAX_PAGE_SIZE;
    }
}
