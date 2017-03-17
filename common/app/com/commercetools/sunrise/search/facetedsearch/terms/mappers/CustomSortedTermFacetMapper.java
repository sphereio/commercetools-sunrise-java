package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetOptionViewModelFactory;
import io.sphere.sdk.search.TermFacetResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the given list of values.
 * Any value that do not appear in the list of values is placed at the end of the output list.
 */
public final class CustomSortedTermFacetMapper implements TermFacetMapper {

    private final List<String> customSortedValues;
    private final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;

    CustomSortedTermFacetMapper(final List<String> customSortedValues, final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory) {
        this.customSortedValues = customSortedValues;
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetResult termFacetResult, final List<String> selectedValues) {
        return termFacetResult.getTerms().stream()
                .map(term -> termFacetOptionViewModelFactory.create(term, selectedValues))
                .sorted(this::comparePositions)
                .collect(toList());
    }

    private int comparePositions(final FacetOptionViewModel left, final FacetOptionViewModel right) {
        final int leftPosition = customSortedValues.indexOf(left.getValue());
        final int rightPosition = customSortedValues.indexOf(right.getValue());
        return comparePositions(leftPosition, rightPosition);
    }

    static int comparePositions(final int leftPosition, final int rightPosition) {
        final int comparison;
        if (leftPosition == rightPosition) {
            comparison = 0;
        } else if (leftPosition < 0) {
            comparison = 1;
        } else if (rightPosition < 0) {
            comparison = -1;
        } else {
            comparison = Integer.compare(leftPosition, rightPosition);
        }
        return comparison;
    }
}
