package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetOptionViewModelFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CustomSortedTermFacetMapperFactory implements TermFacetMapperFactory {

    private TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;

    @Inject
    public CustomSortedTermFacetMapperFactory(final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory) {
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
    }

    @Override
    public CustomSortedTermFacetMapper create(final TermFacetMapperSettings settings) {
        return new CustomSortedTermFacetMapper(settings.getValues(), termFacetOptionViewModelFactory);
    }
}
