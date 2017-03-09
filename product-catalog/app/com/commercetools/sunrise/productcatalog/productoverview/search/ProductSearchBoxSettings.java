package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.searchbox.SearchBoxSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ProductSearchBoxSettings extends SearchBoxSettings {

    private static final String CONFIG = "pop.searchTerm";

    @Inject
    public ProductSearchBoxSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find search box configuration", CONFIG)));
    }

    @Override
    public String getFieldName() {
        return super.getFieldName();
    }

    @Override
    public String getDefaultValue() {
        return super.getDefaultValue();
    }
}