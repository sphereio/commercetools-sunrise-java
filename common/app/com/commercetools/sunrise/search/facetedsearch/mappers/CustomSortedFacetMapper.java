package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormOption;
import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapper;
import io.sphere.sdk.search.TermFacetResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the given list of values.
 * Any value that do not appear in the list of values is placed at the end of the output list.
 */
public class CustomSortedFacetMapper implements FacetMapper {

    private final List<String> sortedFacetValues;

    private CustomSortedFacetMapper(final List<String> sortedFacetValues) {
        this.sortedFacetValues = sortedFacetValues;
    }

    @Override
    public List<TermFacetedSearchFormOption> apply(final TermFacetResult termFacetResult) {
        return termFacetResult.getTerms().stream()
                .map(TermFacetedSearchFormOption::ofTermStats)
                .sorted(this::comparePositions)
                .collect(toList());
    }

    private int comparePositions(final TermFacetedSearchFormOption left, final TermFacetedSearchFormOption right) {
        final int leftPosition = sortedFacetValues.indexOf(left.getValue());
        final int rightPosition = sortedFacetValues.indexOf(right.getValue());
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
