package com.commercetools.sunrise.productcatalog.productoverview.search.searchbox;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.searchbox.ConfigurableSearchBoxSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class ProductSearchBoxSettings extends ConfigurableSearchBoxSettings {

    private static final String CONFIG = "pop.searchTerm";

    @Inject
    public ProductSearchBoxSettings(final Configuration configuration) {
        super(configuration(configuration));
    }

    private static Configuration configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find search box configuration", CONFIG));
    }
}