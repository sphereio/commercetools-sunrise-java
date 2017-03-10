package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.pagination.ConfigurableEntriesPerPageFormSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ProductsPerPageFormSettings extends ConfigurableEntriesPerPageFormSettings {

    private static final String CONFIG = "pop.productsPerPage";

    @Inject
    ProductsPerPageFormSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find products per page configuration", CONFIG)));
    }
}
