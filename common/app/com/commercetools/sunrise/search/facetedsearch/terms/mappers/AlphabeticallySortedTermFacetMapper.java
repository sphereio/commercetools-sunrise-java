package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetOptionViewModelFactory;
import io.sphere.sdk.search.TermFacetResult;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the alphabetical order of the label.
 */
public final class AlphabeticallySortedTermFacetMapper implements TermFacetMapper {

    private final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;

    AlphabeticallySortedTermFacetMapper(final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory) {
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetResult facetResult, final List<String> selectedValues) {
        return facetResult.getTerms().stream()
                .map(term -> termFacetOptionViewModelFactory.create(term, selectedValues))
                .sorted(Comparator.comparing(FacetOptionViewModel::getLabel))
                .collect(toList());
    }
}
