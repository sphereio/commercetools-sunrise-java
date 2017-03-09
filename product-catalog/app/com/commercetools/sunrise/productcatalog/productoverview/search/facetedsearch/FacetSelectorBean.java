package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.common.models.ViewModel;
import io.sphere.sdk.facets.Facet;
import io.sphere.sdk.facets.FacetType;

public class FacetSelectorBean extends ViewModel {

    private Facet facet;

    public FacetSelectorBean() {
    }

    public Facet getFacet() {
        return facet;
    }

    public void setFacet(final Facet facet) {
        this.facet = facet;
    }

    public boolean isDisplayList() {
        return isFacetType(SunriseFacetType.LIST);
    }

    public boolean isSelectFacet() {
        return isFacetType(SunriseFacetType.LIST) || isFacetType(SunriseFacetType.COLUMNS_LIST);
    }

    public boolean isSliderRangeFacet() {
        return isFacetType(SunriseFacetType.SLIDER_RANGE);
    }

    public boolean isBucketRangeFacet() {
        return isFacetType(SunriseFacetType.BUCKET_RANGE);
    }

    public boolean isHierarchicalSelectFacet() {
        return isFacetType(SunriseFacetType.CATEGORY_TREE);
    }

    private boolean isFacetType(final FacetType type) {
        return facet.getType().equals(type);
    }
}
