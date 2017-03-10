package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.sort.ConfigurableSortFormSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ProductSearchSortFormSettings extends ConfigurableSortFormSettings {

    private static final String CONFIG = "pop.sortProducts";

    @Inject
    public ProductSearchSortFormSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find sort configuration", CONFIG)));
    }
}
