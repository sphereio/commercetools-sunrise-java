package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.framework.viewmodels.forms.AbstractFormSettingsWithOptions;
import com.commercetools.sunrise.search.sort.SortFormOption;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.SortExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public abstract class ConfigurableTermFacetedSearchFormSettings extends TermFacetedSearchFormSettingsImpl {

    private static final String CONFIG_KEY = "key";
    private static final String CONFIG_TYPE = "type";
    private static final String CONFIG_LABEL = "label";
    private static final String CONFIG_EXPR = "expr";
    private static final String CONFIG_COUNT = "count";
    private static final String CONFIG_MULTI_SELECT = "multiSelect";
    private static final String CONFIG_MATCHING_ALL = "matchingAll";
    private static final String CONFIG_LIMIT = "limit";
    private static final String CONFIG_THRESHOLD = "threshold";
    private static final String CONFIG_MAPPER = "mapper";

    private static final String CONFIG_MAPPER_TYPE = "type";
    private static final String CONFIG_MAPPER_VALUES = "values";

    public ConfigurableTermFacetedSearchFormSettings(final Configuration configuration) {
        super(key(configuration), options(configuration));
    }

    private static String key(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(CONFIG_KEY))
                .orElseThrow(() -> new SunriseConfigurationException("Missing key to create facet", CONFIG_KEY, configuration));
    }

    private static List<SortFormOption> options(final Configuration configuration) {
        return configuration.getConfigList(CONFIG_OPTIONS, emptyList()).stream()
                .map(ConfigurableTermFacetedSearchFormSettings::initializeFormOption)
                .collect(toList());
    }

    private static SortFormOption initializeFormOption(final Configuration optionConfig) {
        return SortFormOption.of(
                extractLabel(optionConfig),
                extractValue(optionConfig),
                extractExpressions(optionConfig),
                extractIsDefault(optionConfig));
    }

    private static String extractLabel(final Configuration optionConfig) {
        return optionConfig.getString(OPTION_LABEL_ATTR, "");
    }

    private static String extractValue(final Configuration optionConfig) {
        return Optional.ofNullable(optionConfig.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing sort value", OPTION_VALUE_ATTR, CONFIG_OPTIONS));
    }

    private static List<SortExpression<ProductProjection>> extractExpressions(final Configuration optionConfig) {
        return optionConfig.getStringList(OPTION_EXPR_ATTR, emptyList()).stream()
                .filter(expr -> !expr.isEmpty())
                .map(SortExpression::<ProductProjection>of)
                .collect(toList());
    }

    private static Boolean extractIsDefault(final Configuration optionConfig) {
        return optionConfig.getBoolean(OPTION_DEFAULT_ATTR, false);
    }
}
