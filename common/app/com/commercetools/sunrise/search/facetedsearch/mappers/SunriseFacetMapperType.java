package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;

/**
 * Type of the mapper, to map from the facet result received from the platform to a form option.
 */
public enum SunriseFacetMapperType implements FacetMapperType {

    CATEGORY_TREE,
    ALPHABETICALLY_SORTED,
    CUSTOM_SORTED,
    NONE
}
