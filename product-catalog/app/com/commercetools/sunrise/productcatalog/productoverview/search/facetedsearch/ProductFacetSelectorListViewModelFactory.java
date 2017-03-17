package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.viewmodels.AbstractFacetSelectorListViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.viewmodels.BucketRangeFacetSelectorViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.viewmodels.SliderRangeFacetSelectorViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetSelectorViewModelFactory;
import io.sphere.sdk.products.ProductProjection;

import javax.inject.Inject;
import java.util.Locale;

public class ProductFacetSelectorListViewModelFactory extends AbstractFacetSelectorListViewModelFactory<ProductProjection> {

    @Inject
    public ProductFacetSelectorListViewModelFactory(final Locale locale, final ProductFacetedSearchFormSettingsList settingsList,
                                                    final TermFacetSelectorViewModelFactory termFacetSelectorViewModelFactory,
                                                    final BucketRangeFacetSelectorViewModelFactory bucketRangeFacetSelectorViewModelFactory,
                                                    final SliderRangeFacetSelectorViewModelFactory sliderRangeFacetSelectorViewModelFactory) {
        super(locale, settingsList, termFacetSelectorViewModelFactory, bucketRangeFacetSelectorViewModelFactory, sliderRangeFacetSelectorViewModelFactory);
    }
}
