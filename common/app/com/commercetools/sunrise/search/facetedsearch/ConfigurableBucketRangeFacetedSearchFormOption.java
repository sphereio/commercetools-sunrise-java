package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import play.Configuration;

import java.util.Optional;

final class ConfigurableBucketRangeFacetedSearchFormOption extends BucketRangeFacetedSearchFormOptionImpl {

    private static final String OPTION_LABEL_ATTR = "label";
    private static final String OPTION_VALUE_ATTR = "value";
    private static final String OPTION_RANGE_ATTR = "range";

    ConfigurableBucketRangeFacetedSearchFormOption(final Configuration configuration) {
        super(fieldLabel(configuration), fieldValue(configuration), range(configuration));
    }

    private static String fieldLabel(final Configuration configuration) {
        return configuration.getString(OPTION_LABEL_ATTR, "");
    }

    private static String fieldValue(final Configuration configuration) {
        return Optional.ofNullable(configuration.getString(OPTION_VALUE_ATTR))
                .orElseThrow(() -> new SunriseConfigurationException("Missing bucket facet field value", OPTION_VALUE_ATTR, configuration));
    }

    private static String range(final Configuration configuration) {
        return configuration.getString(OPTION_RANGE_ATTR, "");
    }
}
