package com.commercetools.sunrise.search.facetedsearch.terms.mappers;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.viewmodels.TermFacetOptionViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.search.TermFacetResult;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the given list of values.
 * Any value that do not appear in the list of values is placed at the end of the output list.
 */
@RequestScoped
public final class CustomSortedTermFacetMapper implements TermFacetMapper {

    private final Http.Context httpContext;
    private final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory;

    @Inject
    CustomSortedTermFacetMapper(final Http.Context httpContext,
                                final TermFacetOptionViewModelFactory termFacetOptionViewModelFactory) {
        this.httpContext = httpContext;
        this.termFacetOptionViewModelFactory = termFacetOptionViewModelFactory;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult termFacetResult) {
        final List<String> customSortedValues = customSortedValues(settings);
        final List<String> selectedValues = settings.getAllSelectedValues(httpContext);
        return termFacetResult.getTerms().stream()
                .map(term -> termFacetOptionViewModelFactory.create(term, selectedValues))
                .sorted((left, right) -> comparePositions(left, right, customSortedValues))
                .collect(toList());
    }

    private int comparePositions(final FacetOptionViewModel left, final FacetOptionViewModel right, final List<String> customSortedValues) {
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

    private static List<String> customSortedValues(final TermFacetedSearchFormSettings<?> settings) {
        if (settings.getMapperSettings() != null && settings.getMapperSettings().getValues() != null) {
            return settings.getMapperSettings().getValues();
        } else {
            return emptyList();
        }
    }
}
