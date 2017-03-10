package com.commercetools.sunrise.search.pagination;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import play.Configuration;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class EntriesPerPageFormSettings extends AbstractFormSettingsWithOptions<EntriesPerPageFormOption> {

    private static final int MIN_PAGE_SIZE = 0;
    private static final int MAX_PAGE_SIZE = 500;

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "pp";

    private static final String CONFIG_OPTIONS = "options";
    private static final String OPTION_LABEL_ATTR = "label";
    private static final String OPTION_VALUE_ATTR = "value";
    private static final String OPTION_AMOUNT_ATTR = "amount";
    private static final String OPTION_DEFAULT_ATTR = "default";

    protected EntriesPerPageFormSettings(final Configuration configuration) {
        super(key(configuration), options(configuration));
    }

    public static EntriesPerPageFormSettings of(final Configuration configuration) {
        return new EntriesPerPageFormSettings(configuration);
    }

    private static String key(final Configuration configuration) {
        return configuration.getString(CONFIG_KEY, DEFAULT_KEY);
    }

    private static List<EntriesPerPageFormOption> options(final Configuration configuration) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(EntriesPerPageFormSettings::initializeOption)
                .collect(toList());
    }

    private static EntriesPerPageFormOption initializeOption(final Configuration optionConfig) {
        return EntriesPerPageFormOption.of(
                extractLabel(optionConfig),
                extractValue(optionConfig),
                extractAmount(optionConfig),
                extractIsDefault(optionConfig));
    }

    private static String extractLabel(final Configuration optionConfig) {
        return optionConfig.getString(OPTION_LABEL_ATTR, "");
    }

    private static String extractValue(final Configuration optionConfig) {
        return Optional.ofNullable(optionConfig.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page value", OPTION_VALUE_ATTR, CONFIG_OPTIONS));
    }

    private static int extractAmount(final Configuration optionConfig) {
        final int amount = Optional.ofNullable(optionConfig.getInt(OPTION_AMOUNT_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing elements per page amount", OPTION_AMOUNT_ATTR, CONFIG_OPTIONS));
        if (!isValidAmount(amount)) {
            throw new SunriseConfigurationException(String.format("Elements per page options are not within bounds [%d, %d]: %s",
                    MIN_PAGE_SIZE, MAX_PAGE_SIZE, optionConfig), OPTION_AMOUNT_ATTR, CONFIG_OPTIONS);
        }
        return amount;
    }

    private static Boolean extractIsDefault(final Configuration optionConfig) {
        return optionConfig.getBoolean(OPTION_DEFAULT_ATTR, false);
    }

    private static boolean isValidAmount(final int amount) {
        return amount >= MIN_PAGE_SIZE && amount <= MAX_PAGE_SIZE;
    }
}
