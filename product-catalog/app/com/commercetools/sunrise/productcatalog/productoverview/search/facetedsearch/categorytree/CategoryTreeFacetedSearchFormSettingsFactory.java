package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.categorytree.CategoryTreeConfiguration;
import com.commercetools.sunrise.framework.SunriseModel;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;

import javax.inject.Inject;
import java.util.Locale;

public class CategoryTreeFacetedSearchFormSettingsFactory extends SunriseModel {

    private final Locale locale;
    private final CategoryFinder categoryFinder;
    private final CategoryTreeConfiguration categoryTreeConfiguration;

    @Inject
    public CategoryTreeFacetedSearchFormSettingsFactory(final Locale locale, final CategoryFinder categoryFinder,
                                                        final CategoryTreeConfiguration categoryTreeConfiguration) {
        this.locale = locale;
        this.categoryFinder = categoryFinder;
        this.categoryTreeConfiguration = categoryTreeConfiguration;
    }

    public CategoryTreeFacetedSearchFormSettings create(final ConfiguredCategoryTreeFacetedSearchFormSettings configuration) {
        return new CategoryTreeFacetedSearchFormSettingsImpl(configuration, locale, categoryFinder, categoryTreeConfiguration);
    }
}
