package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

/**
 * Type of the mapper, to map from the facet result received from the platform to a form option.
 */
public interface TermFacetMapperType {

    String name();

    String value();

    Class<? extends TermFacetMapper> mapper();
}
