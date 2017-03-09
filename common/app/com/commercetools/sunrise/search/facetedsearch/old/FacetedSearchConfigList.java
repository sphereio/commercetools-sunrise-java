package com.commercetools.sunrise.search.facetedsearch.old;

import io.sphere.sdk.models.Base;

import java.util.List;

public final class FacetedSearchConfigList extends Base {

    private final List<SelectFacetedSearchConfig> selectFacetedSearchConfigList;
    private final List<RangeFacetedSearchConfig> rangeFacetedSearchConfigList;


    private FacetedSearchConfigList(final List<SelectFacetedSearchConfig> selectFacetedSearchConfigList,
                                    final List<RangeFacetedSearchConfig> rangeFacetedSearchConfigList) {
        this.selectFacetedSearchConfigList = selectFacetedSearchConfigList;
        this.rangeFacetedSearchConfigList = rangeFacetedSearchConfigList;
    }

    public List<SelectFacetedSearchConfig> getSelectFacetedSearchConfigList() {
        return selectFacetedSearchConfigList;
    }

    public List<RangeFacetedSearchConfig> getRangeFacetedSearchConfigList() {
        return rangeFacetedSearchConfigList;
    }

    public static FacetedSearchConfigList of(final List<SelectFacetedSearchConfig> selectFacetConfigList,
                                             final List<RangeFacetedSearchConfig> rangeFacetedSearchConfigList) {
        return new FacetedSearchConfigList(selectFacetConfigList, rangeFacetedSearchConfigList);
    }
}
