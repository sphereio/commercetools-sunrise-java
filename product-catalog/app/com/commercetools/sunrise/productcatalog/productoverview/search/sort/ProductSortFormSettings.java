package com.commercetools.sunrise.productcatalog.productoverview.search.sort;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.sort.ConfigurableSortFormOption;
import com.commercetools.sunrise.search.sort.ConfigurableSortFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class ProductSortFormSettings extends ConfigurableSortFormSettings<ProductProjection> {

    private static final String CONFIG = "pop.sortProducts";

    @Inject
    public ProductSortFormSettings(final Configuration configuration) {
        super(configuration(configuration), ConfigurableSortFormOption::new);
    }

    private static Configuration configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find sort configuration", CONFIG));
    }
}
