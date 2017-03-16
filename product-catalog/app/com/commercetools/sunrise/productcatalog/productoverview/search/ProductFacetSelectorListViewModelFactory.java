package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.facetedsearch.viewmodels.AbstractFacetSelectorListViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.BucketRangeFacetSelectorViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.SliderRangeFacetSelectorViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.TermFacetSelectorViewModelFactory;
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
