package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.annotation.Nullable;

/**
 * Type of the mapper, to map from the facet result received from the platform to a form option.
 */
public interface TermFacetMapperType {

    String name();

    String value();

    @Nullable
    Class<? extends TermFacetMapperFactory> factory();

}
