package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperType;
import play.Configuration;

import java.util.List;

class ConfigurableRangeFacetedSearchFormSettings<T> extends ConfigurableFacetedSearchFormSettings<T> implements RangeFacetedSearchFormSettings<T> {

    protected ConfigurableRangeFacetedSearchFormSettings(final Configuration configuration, final int position,
                                                         final List<? extends FacetUIType> facetUITypes,
                                                         final List<? extends FacetMapperType> facetMapperTypes) {
        super(configuration, position, facetUITypes, facetMapperTypes);
    }
}
