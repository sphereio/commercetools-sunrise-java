package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.viewmodels.TermFacetOptionViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AlphabeticallySortedTermFacetMapperFactory implements TermFacetMapperFactory {

    private TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;

    @Inject
    public AlphabeticallySortedTermFacetMapperFactory(final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory) {
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
    }

    @Override
    public AlphabeticallySortedTermFacetMapper create(final TermFacetMapperSettings settings) {
        return new AlphabeticallySortedTermFacetMapper(termFacetOptionViewModelFactory);
    }
}
