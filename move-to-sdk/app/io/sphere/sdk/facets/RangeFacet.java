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
public interface RangeFacet<T> extends Facet<T> {

    /**
     * Gets the complete options without limitations.
     * @return the complete possible options
     */
    List<FacetOption> getAllOptions();

    /**
     * Obtains the truncated options list according to the defined limit.
     * @return the truncated options if the limit is defined, the whole list otherwise
     */
    List<FacetOption> getLimitedOptions();

    /**
     * Gets the mapper for this facet.
     * @return the facet option mapper, or absent if there is no mapper
     */
    @Nullable
    FacetOptionMapper getMapper();

    /**
     * Gets the threshold indicating the minimum amount of options allowed to be displayed in the facet.
     * @return the threshold for the amount of options that can be displayed, or absent if it has no threshold
     */
    @Nullable
    Long getThreshold();

    /**
     * Gets the limit for the maximum amount of options allowed to be displayed in the facet.
     * @return the limit for the amount of options that can be displayed, or absent if it has no limit
     */
    @Nullable
    Long getLimit();

    /**
     * Gets the associated term facet result for this select facet.
     * @return the term facet result, or absent if there is no associated facet result
     */
    @Nullable
    RangeFacetResult getFacetResult();

    RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel();

    RangeFacet<T> withSearchResult(final PagedSearchResult<T> searchResult);
}
