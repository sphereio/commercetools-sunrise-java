package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.ConfigurableCategoryTreeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.SimpleFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.SimpleFacetedSearchFormSettingsListFactory;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class SimpleProductFacetedSearchFormSettingsListFactory extends SimpleFacetedSearchFormSettingsListFactory {

    private static final String CONFIG = "pop.facets";
    private static final String CONFIG_TYPE_CATEGORY_TREE = "categoryTree";

    @Inject
    public SimpleProductFacetedSearchFormSettingsListFactory(final Configuration configuration) {
        super(configuration(configuration));
    }

    @Override
    protected void addSettingsToList(final List<SimpleFacetedSearchFormSettings> list, final Configuration configuration, final String type) {
        if (type.equals(CONFIG_TYPE_CATEGORY_TREE)) {
            list.add(new ConfigurableCategoryTreeFacetedSearchFormSettings(configuration));
        } else {
            super.addSettingsToList(list, configuration, type);
        }
    }

    private static List<Configuration> configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfigList(CONFIG)).orElseGet(Collections::emptyList);
    }
}
