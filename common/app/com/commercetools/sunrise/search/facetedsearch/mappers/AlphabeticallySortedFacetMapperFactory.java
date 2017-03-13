package com.commercetools.sunrise.search.facetedsearch.mappers;

import javax.inject.Singleton;

@Singleton
public final class AlphabeticallySortedFacetMapperFactory implements FacetMapperFactory {

    @Override
    public TermFacetMapper create(final FacetMapperSettings settings) {
        return new AlphabeticallySortedTermFacetMapper();
    }
}
