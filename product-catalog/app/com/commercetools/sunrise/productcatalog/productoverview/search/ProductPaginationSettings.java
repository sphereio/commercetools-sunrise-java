package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.pagination.PaginationSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ProductPaginationSettings extends PaginationSettings {

    private static final String CONFIG = "pop.pagination";

    @Inject
    ProductPaginationSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find product pagination configuration", CONFIG)));
    }

    @Override
    public String getFieldName() {
        return super.getFieldName();
    }

    @Override
    public Integer getDefaultValue() {
        return super.getDefaultValue();
    }

    @Override
    public int getDisplayedPages() {
        return super.getDisplayedPages();
    }
}