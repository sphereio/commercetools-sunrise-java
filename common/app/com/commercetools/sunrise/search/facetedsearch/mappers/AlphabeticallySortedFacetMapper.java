package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.framework.viewmodels.forms.FormOption;
import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormOption;
import io.sphere.sdk.search.TermFacetResult;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the alphabetical order of the label.
 */
public class AlphabeticallySortedFacetMapper implements FacetMapper {

    @Override
    public List<TermFacetedSearchFormOption> apply(final TermFacetResult termFacetResult) {
        return termFacetResult.getTerms().stream()
                .map(TermFacetedSearchFormOption::ofTermStats)
                .sorted(Comparator.comparing(FormOption::getFieldLabel))
                .collect(toList());
    }
}
