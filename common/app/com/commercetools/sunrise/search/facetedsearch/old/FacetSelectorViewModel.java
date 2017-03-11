package com.commercetools.sunrise.search.facetedsearch.old;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;
import com.commercetools.sunrise.search.facetedsearch.SunriseFacetUIType;
import io.sphere.sdk.facets.Facet;
import io.sphere.sdk.facets.FacetType;

public class FacetSelectorViewModel extends ViewModel {

    private Facet facet;

    public FacetSelectorViewModel() {
    }

    public Facet getFacet() {
        return facet;
    }

    public void setFacet(final Facet facet) {
        this.facet = facet;
    }

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

    private boolean isFacetType(final FacetType type) {
        return facet.getType().equals(type);
    }
}
