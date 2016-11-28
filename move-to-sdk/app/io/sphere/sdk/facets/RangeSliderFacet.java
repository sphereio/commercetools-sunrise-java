package io.sphere.sdk.facets;

import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public interface RangeSliderFacet<T> extends Facet<T> {

    @Nullable
    RangeFacetResult getFacetResult();

    FacetRange getSelectedRange();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    RangeSliderFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);

    RangeSliderFacet<T> withSelectedRange(final FacetRange selectedRange);
}
