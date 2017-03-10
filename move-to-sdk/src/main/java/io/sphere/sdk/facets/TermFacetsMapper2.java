package io.sphere.sdk.facets;

import io.sphere.sdk.search.TermFacetResult;

import java.util.List;
import java.util.function.Function;

/**
 * Mapper class that allows to transform the facet options, as they are extracted from the search result, into another list.
 * For example, to transform a list of category IDs into a hierarchical structure of categories with localized names.
 */
@FunctionalInterface
public interface TermFacetsMapper2 extends Function<TermFacetResult, List<FacetOption>> {

    /**
     * Transforms the given list of facet options into a different list of facet options.
     * @param termFacetResult the facet result to be transformed into
     * @return the transformed options list
     */
    @Override
    List<TermFacetedO> apply(TermFacetResult termFacetResult);
}
