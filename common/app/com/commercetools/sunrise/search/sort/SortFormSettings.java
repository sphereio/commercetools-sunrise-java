package com.commercetools.sunrise.search.sort;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;
import play.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class SortFormSettings<T> extends FormSettingsWithOptions<SortFormOption<T>> {

    private static final String CONFIG_KEY = "key";
    private static final String DEFAULT_KEY = "sort";

    private static final String CONFIG_OPTIONS = "options";
    private static final String OPTION_LABEL_ATTR = "label";
    private static final String OPTION_VALUE_ATTR = "value";
    private static final String OPTION_EXPR_ATTR = "expr";
    private static final String OPTION_DEFAULT_ATTR = "default";

    protected SortFormSettings(final Configuration configuration, final Function<String, T> creatorMapper,
                               final BiFunction<T, Locale, T> localizationMapper) {
        super(key(configuration), options(configuration, creatorMapper, localizationMapper));
    }

    public static <T> SortFormSettings<T> of(final Configuration configuration, final Function<String, T> creatorMapper,
                                             final BiFunction<T, Locale, T> localizationMapper) {
        return new SortFormSettings<>(configuration, creatorMapper, localizationMapper);
    }

    private static String key(final Configuration configuration) {
        return configuration.getString(CONFIG_KEY, DEFAULT_KEY);
    }

    private static <T> List<SortFormOption<T>> options(final Configuration configuration, final Function<String, T> creatorMapper,
                                                       final BiFunction<T, Locale, T> localizationMapper) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(config -> initializeFormOption(config, creatorMapper, localizationMapper))
                .collect(toList());
    }

    private static <T> SortFormOption<T> initializeFormOption(final Configuration optionConfig, final Function<String, T> creatorMapper,
                                                              final BiFunction<T, Locale, T> localizationMapper) {
        return SortFormOption.of(
                extractLabel(optionConfig),
                extractValue(optionConfig),
                extractExpressions(optionConfig, creatorMapper),
                extractIsDefault(optionConfig),
                localizationMapper);
    }

    private static String extractLabel(final Configuration optionConfig) {
        return optionConfig.getString(OPTION_LABEL_ATTR, "");
    }

    private static String extractValue(final Configuration optionConfig) {
        return Optional.ofNullable(optionConfig.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing sort value", OPTION_VALUE_ATTR, CONFIG_OPTIONS));
    }

    private static <T> List<T> extractExpressions(final Configuration optionConfig, final Function<String, T> creatorMapper) {
        return optionConfig.getStringList(OPTION_EXPR_ATTR, emptyList()).stream()
                .filter(expr -> !expr.isEmpty())
                .map(creatorMapper)
                .collect(toList());
    }

    private static Boolean extractIsDefault(final Configuration optionConfig) {
        return optionConfig.getBoolean(OPTION_DEFAULT_ATTR, false);
    }
}
