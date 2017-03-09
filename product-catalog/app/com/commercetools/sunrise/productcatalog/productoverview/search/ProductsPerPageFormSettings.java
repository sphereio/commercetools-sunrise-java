package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.pagination.EntriesPerPageFormOption;
import com.commercetools.sunrise.search.pagination.EntriesPerPageFormSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public final class ProductsPerPageFormSettings extends EntriesPerPageFormSettings {

    private static final String CONFIG = "pop.productsPerPage";

    @Inject
    ProductsPerPageFormSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find products per page configuration", CONFIG)));
    }

    @Override
    public String getFieldName() {
        return super.getFieldName();
    }

    @Override
    public List<EntriesPerPageFormOption> getOptions() {
        return super.getOptions();
    }

    @Override
    public Optional<EntriesPerPageFormOption> findDefaultOption() {
        return super.findDefaultOption();
    }
}
