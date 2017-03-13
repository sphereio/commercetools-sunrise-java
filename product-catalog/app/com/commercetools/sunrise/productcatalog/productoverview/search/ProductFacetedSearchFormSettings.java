package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.facetedsearch.ConfigurableFacetedSearchFormSettings;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Singleton
public final class ProductFacetedSearchFormSettings extends ConfigurableFacetedSearchFormSettings {

    private static final String CONFIG = "pop.facets";

    @Inject
    public ProductFacetedSearchFormSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find faceted search configuration", CONFIG)), 2, facetUITypes(), facetMapperTypes());
    }

    private static List<SunriseFacetUIType> facetUITypes() {
        return asList(SunriseFacetUIType.values());
    }

    private static List<SunriseFacetMapperType> facetMapperTypes() {
        return asList(SunriseFacetMapperType.values());
    }

}
