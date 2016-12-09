package io.sphere.sdk.facets;

import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Facet that contains a list of range  options.
 * @param <T> type of the resource for this facet (e.g. ProductProjection)
 */
public interface RangeFacet<T> extends Facet<T> {

    /**
     * Gets the associated range facet result for this range facet.
     * @return the range facet result, or absent if there is no associated facet result
     */
    @Nullable
    RangeFacetResult getFacetResult();

    List<FacetRange<String>> getInitialRanges();

    List<FilterRange<String>> getSelectedRanges();

    List<RangeOption> getOptions();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    @Override
    RangeFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);

    RangeFacet<T> withSelectedRanges(final List<FilterRange<String>> selectedRanges);

    RangeFacet<T> withInitialRanges(final List<FacetRange<String>> initialRanges);
}
