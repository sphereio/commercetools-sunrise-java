package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsListFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;

import javax.inject.Inject;
import java.util.Locale;

@RequestScoped
public class ProductFacetedSearchFormSettingsListFactory extends FacetedSearchFormSettingsListFactory<ProductProjection> {

    private final CategoryFinder categoryFinder;

    @Inject
    public ProductFacetedSearchFormSettingsListFactory(final SimpleProductFacetedSearchFormSettingsList settingsList,
                                                       final Locale locale, final CategoryFinder categoryFinder) {
        super(settingsList, locale);
        this.categoryFinder = categoryFinder;
    }

    @Override
    protected TermFacetedSearchFormSettings<ProductProjection> createTermSettings(final PositionedSettings<SimpleTermFacetedSearchFormSettings<ProductProjection>> positioned, final Locale locale) {
        if (positioned.getSettings() instanceof SimpleCategoryTreeFacetedSearchFormSettings) {
            final SimpleCategoryTreeFacetedSearchFormSettings settings = (SimpleCategoryTreeFacetedSearchFormSettings) positioned.getSettings();
            return new CategoryTreeFacetedSearchFormSettings(settings, locale, categoryFinder);
        } else {
            return super.createTermSettings(positioned, locale);
        }
    }
}
