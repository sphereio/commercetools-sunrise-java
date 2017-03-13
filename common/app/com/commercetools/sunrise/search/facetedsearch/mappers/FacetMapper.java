package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormOption;
import io.sphere.sdk.search.FacetResult;

import java.util.List;
import java.util.function.Function;

/**
 * Mapper class that allows to transform a term facet result, as they are extracted from the search result, into form options.
 * For example, to transform a list of category IDs coming from a facet search request into a hierarchical structure of categories with localized names.
 */
@FunctionalInterface
public interface FacetMapper extends Function<FacetResult, List<? extends FacetedSearchFormOption>> {

    /**
     * Transforms the given facet result into a list of faceted search options.
     * @param facetResult the facet result to be transformed into the list of faceted search options
     * @return the transformed faceted search options
     */
    @Override
    List<? extends FacetedSearchFormOption> apply(FacetResult facetResult);
}
