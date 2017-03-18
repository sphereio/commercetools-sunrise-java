package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.TermFacetResult;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Mapper class that allows to transform a term facet result, as they are extracted from the search result, into form options.
 * For example, to transform a list of category IDs coming from a facet search request into a hierarchical structure of categories with localized names.
 */
@FunctionalInterface
public interface TermFacetMapper extends BiFunction<TermFacetedSearchFormSettings<?>, TermFacetResult, List<FacetOptionViewModel>> {

    /**
     * Transforms the given facet result into a list of faceted search options.
     * @param facetResult the facet result to be transformed into the list of faceted search options
     * @return the transformed faceted search options
     */
    List<FacetOptionViewModel> apply(TermFacetedSearchFormSettings<?> settings, TermFacetResult facetResult);
}
