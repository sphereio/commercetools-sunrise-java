package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormOption;
import io.sphere.sdk.search.FacetResult;

import java.util.List;

/**
 * Mapper class that allows to transform a term facet result, as they are extracted from the search result, into form options.
 * For example, to transform a list of category IDs coming from a facet search result into a hierarchical structure of categories with localized names.
 */
@FunctionalInterface
public interface TermFacetMapper extends FacetMapper {

    /**
     * Transforms the given term facet result into a list of faceted search options.
     * @param facetResult the term facet result to be transformed into the list of faceted search options
     * @return the transformed faceted search options
     */
    @Override
    List<TermFacetedSearchFormOption> apply(final FacetResult facetResult);
}
