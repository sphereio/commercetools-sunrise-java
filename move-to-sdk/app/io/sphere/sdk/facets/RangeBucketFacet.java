package io.sphere.sdk.facets;

import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Facet that contains a list of range bucket options.
 * @param <T> type of the resource for this facet (e.g. ProductProjection)
 */
public interface RangeBucketFacet<T> extends Facet<T> {

    /**
     * Gets the associated range facet result for this range facet.
     * @return the range facet result, or absent if there is no associated facet result
     */
    @Nullable
    RangeFacetResult getFacetResult();

    List<FacetRange> getSelectedRanges();

    List<RangeBucketOption> getFacetOptions();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    RangeBucketFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);

    RangeBucketFacet<T> withSelectedRanges(final List<FacetRange> selectedRanges);
}
