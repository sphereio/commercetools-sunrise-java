package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.DefaultCategoryTreeFacetedSearchFormSettings;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.SimpleCategoryTreeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsListFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;

import javax.inject.Inject;
import java.util.Locale;

@RequestScoped
public class ProductFacetedSearchFormSettingsListFactory extends FacetedSearchFormSettingsListFactory<ProductProjection> {

    private final CategoryFinder categoryFinder;

    @Inject
    public ProductFacetedSearchFormSettingsListFactory(final SimpleProductFacetedSearchFormSettingsListFactory simpleSettingsFactory,
                                                       final Locale locale, final CategoryFinder categoryFinder) {
        super(simpleSettingsFactory.create(), locale);
        this.categoryFinder = categoryFinder;
    }

    @Override
    protected FacetedSearchFormSettings<ProductProjection> createTermSettings(final SimpleTermFacetedSearchFormSettings settings) {
        if (settings instanceof SimpleCategoryTreeFacetedSearchFormSettings) {
            return new DefaultCategoryTreeFacetedSearchFormSettings((SimpleCategoryTreeFacetedSearchFormSettings) settings, getLocale(), categoryFinder);
        } else {
            return super.createTermSettings(settings);
        }
    }
}
