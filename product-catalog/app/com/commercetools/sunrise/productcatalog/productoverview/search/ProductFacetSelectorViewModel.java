package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.search.facetedsearch.FacetUIType;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetSelectorViewModel;

public class ProductFacetSelectorViewModel extends FacetSelectorViewModel {


    // move to factory
    public boolean isDisplayList() {
        return isFacetType(SunriseFacetUIType.LIST);
    }

    public boolean isSelectFacet() {
        return isFacetType(SunriseFacetUIType.LIST) || isFacetType(SunriseFacetUIType.COLUMNS_LIST);
    }

    public boolean isSliderRangeFacet() {
        return isFacetType(SunriseFacetUIType.SLIDER_RANGE);
    }

    public boolean isBucketRangeFacet() {
        return isFacetType(SunriseFacetUIType.BUCKET_RANGE);
    }

    public boolean isHierarchicalSelectFacet() {
        return isFacetType(SunriseFacetUIType.CATEGORY_TREE);
    }

    private boolean isFacetType(final FacetUIType type) {
        if (getFacet() != null) {
            return getFacet().getType().equals(type);
        }
    }
}
