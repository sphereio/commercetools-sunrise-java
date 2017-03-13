package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.framework.viewmodels.forms.FormOption;
import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormOption;
import io.sphere.sdk.search.FacetResult;
import io.sphere.sdk.search.TermFacetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the alphabetical order of the label.
 */
public final class AlphabeticallySortedTermFacetMapper implements TermFacetMapper {

    private static Logger LOGGER = LoggerFactory.getLogger(AlphabeticallySortedTermFacetMapper.class);

    @Override
    public List<TermFacetedSearchFormOption> apply(final FacetResult facetResult) {
        if (facetResult instanceof TermFacetResult) {
            return ((TermFacetResult) facetResult).getTerms().stream()
                    .map(TermFacetedSearchFormOption::ofTermStats)
                    .sorted(Comparator.comparing(FormOption::getFieldLabel))
                    .collect(toList());
        } else {
            LOGGER.error("Wrong usage of this facet mapper, it can only be used with TermFacetResult, used with " + facetResult.getClass().getSimpleName());
            return emptyList();
        }
    }
}
