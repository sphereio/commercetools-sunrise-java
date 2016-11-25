package io.sphere.sdk.facets;

import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Facet that contains a list of range options.
 * @param <T> type of the resource for this facet (e.g. ProductProjection)
 */
public interface RangeBucketFacet<T> extends Facet<T> {

    /**
     * Gets the associated term facet result for this select facet.
     * @return the term facet result, or absent if there is no associated facet result
     */
    @Nullable
    RangeFacetResult getFacetResult();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    RangeBucketFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);
}
