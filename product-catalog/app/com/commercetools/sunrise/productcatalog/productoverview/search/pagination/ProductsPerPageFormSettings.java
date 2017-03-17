package com.commercetools.sunrise.productcatalog.productoverview.search.pagination;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.pagination.ConfigurableEntriesPerPageFormOption;
import com.commercetools.sunrise.search.pagination.ConfigurableEntriesPerPageFormSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class ProductsPerPageFormSettings extends ConfigurableEntriesPerPageFormSettings {

    private static final String CONFIG = "pop.productsPerPage";

    @Inject
    public ProductsPerPageFormSettings(final Configuration configuration) {
        super(configuration(configuration), ConfigurableEntriesPerPageFormOption::new);
    }

    private static Configuration configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find products per page configuration", CONFIG));
    }
}
