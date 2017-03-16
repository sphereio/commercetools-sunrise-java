package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import java.util.Optional;

final class ConfigurableEntriesPerPageFormOption extends EntriesPerPageFormOptionImpl {

    private static final String OPTION_LABEL_ATTR = "label";
    private static final String OPTION_VALUE_ATTR = "fieldValue";
    private static final String OPTION_AMOUNT_ATTR = "amount";
    private static final String OPTION_DEFAULT_ATTR = "default";

    private static final int MIN_PAGE_SIZE = 0;
    private static final int MAX_PAGE_SIZE = 500;

    protected ConfigurableEntriesPerPageFormOption(final Configuration configuration) {
        super(fieldLabel(configuration), fieldValue(configuration), amount(configuration), isDefault(configuration));
    }

    private static String fieldLabel(final Configuration optionConfig) {
        return optionConfig.getString(OPTION_LABEL_ATTR, "");
    }

    private static String fieldValue(final Configuration optionConfig) {
        return Optional.ofNullable(optionConfig.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page value", OPTION_VALUE_ATTR, optionConfig));
    }

    private static int amount(final Configuration optionConfig) {
        final int amount = Optional.ofNullable(optionConfig.getInt(OPTION_AMOUNT_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page amount", OPTION_AMOUNT_ATTR, optionConfig));
        if (!isValidAmount(amount)) {
            throw new SunriseConfigurationException(String.format("Elements per page option is not within bounds [%d, %d]: %s",
                    MIN_PAGE_SIZE, MAX_PAGE_SIZE, amount), OPTION_AMOUNT_ATTR, optionConfig);
        }
        return amount;
    }

    private static Boolean isDefault(final Configuration optionConfig) {
        return optionConfig.getBoolean(OPTION_DEFAULT_ATTR, false);
    }

    private static boolean isValidAmount(final int amount) {
        return amount >= MIN_PAGE_SIZE && amount <= MAX_PAGE_SIZE;
    }
}
