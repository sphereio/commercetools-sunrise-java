package io.sphere.sdk.facets;

import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public interface SliderRangeFacet<T> extends Facet<T> {

    @Nullable
    RangeFacetResult getFacetResult();

    FilterRange<String> getSelectedRange();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    SliderRangeFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);

    SliderRangeFacet<T> withSelectedRange(final FilterRange<String> selectedRange);
}
