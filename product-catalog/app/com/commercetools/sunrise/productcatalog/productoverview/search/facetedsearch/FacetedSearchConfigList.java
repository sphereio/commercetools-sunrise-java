package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import io.sphere.sdk.models.Base;

import java.util.List;

public final class FacetedSearchConfigList extends Base {

    private final List<SelectFacetedSearchConfig> selectFacetedSearchConfigList;
    private final List<RangeBucketFacetedSearchConfig> rangeBucketFacetedSearchConfigList;
    private final List<RangeSliderFacetedSearchConfig> rangeSliderFacetedSearchConfigList;


    private FacetedSearchConfigList(final List<SelectFacetedSearchConfig> selectFacetedSearchConfigList,
                                    final List<RangeBucketFacetedSearchConfig> rangeBucketFacetedSearchConfigList,
                                    final List<RangeSliderFacetedSearchConfig> rangeSliderFacetedSearchConfigList) {
        this.selectFacetedSearchConfigList = selectFacetedSearchConfigList;
        this.rangeBucketFacetedSearchConfigList = rangeBucketFacetedSearchConfigList;
        this.rangeSliderFacetedSearchConfigList = rangeSliderFacetedSearchConfigList;
    }

    public List<SelectFacetedSearchConfig> getSelectFacetedSearchConfigList() {
        return selectFacetedSearchConfigList;
    }

    public List<RangeSliderFacetedSearchConfig> getRangeSliderFacetedSearchConfig() {
        return rangeSliderFacetedSearchConfigList;
    }

    public List<RangeBucketFacetedSearchConfig> getRangeBucketFacetedSearchConfig() {
        return rangeBucketFacetedSearchConfigList;
    }

    public static FacetedSearchConfigList of(final List<SelectFacetedSearchConfig> selectFacetConfigList,
                                             final List<RangeBucketFacetedSearchConfig> rangeBucketFacetedSearchConfigList,
                                             final List<RangeSliderFacetedSearchConfig> rangeSliderFacetedSearchConfigList) {
        return new FacetedSearchConfigList(selectFacetConfigList, rangeBucketFacetedSearchConfigList, rangeSliderFacetedSearchConfigList);
    }
}
