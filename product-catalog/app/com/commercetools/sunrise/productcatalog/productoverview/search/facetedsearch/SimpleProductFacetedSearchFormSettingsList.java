package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.PositionedSettings;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.ConfigurableCategoryTreeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.ConfigurableFacetedSearchFormSettingsList;
import com.commercetools.sunrise.search.facetedsearch.bucketranges.SimpleBucketRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.sliderranges.SimpleSliderRangeFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class SimpleProductFacetedSearchFormSettingsList extends ConfigurableFacetedSearchFormSettingsList<ProductProjection> {

    private static final String CONFIG = "pop.facets";
    private static final String CONFIG_TYPE_CATEGORY_TREE = "categoryTree";

    @Inject
    public SimpleProductFacetedSearchFormSettingsList(final Configuration configuration) {
        super(configuration(configuration));
    }

    @Override
    protected SimpleTermFacetedSearchFormSettings<ProductProjection> createTermSettings(final Configuration configuration) {
        return new ProductTermFacetedSearchFormSettings(configuration);
    }

    @Override
    protected SimpleSliderRangeFacetedSearchFormSettings<ProductProjection> createSliderRangeSettings(final Configuration configuration) {
        return new ProductSliderRangeFacetedSearchFormSettings(configuration);
    }

    @Override
    protected SimpleBucketRangeFacetedSearchFormSettings<ProductProjection> createBucketRangeSettings(final Configuration configuration) {
        return new ProductBucketRangeFacetedSearchFormSettings(configuration);
    }

    @Override
    protected void handleUnknownFacet(final Configuration configuration, final String type, final int position) {
        if (type.equals(CONFIG_TYPE_CATEGORY_TREE)) {
            getSimpleTermSettings().add(PositionedSettings.of(position, new ConfigurableCategoryTreeFacetedSearchFormSettings(configuration)));
        } else {
            super.handleUnknownFacet(configuration, type, position);
        }
    }

    private static List<Configuration> configuration(final Configuration configuration) {
        return Optional.ofNullable(configuration.getConfigList(CONFIG)).orElseGet(Collections::emptyList);
    }
}
