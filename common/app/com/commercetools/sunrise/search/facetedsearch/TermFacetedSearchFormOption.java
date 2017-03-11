package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.TermStats;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public interface TermFacetedSearchFormOption extends FacetedSearchFormOption {

    /**
     * Gets the children options of this facet option.
     * @return the children options
     */
    List<TermFacetedSearchFormOption> getChildren();

    static TermFacetedSearchFormOption of(final String fieldLabel, final String fieldValue, final String value,
                                          final long count, final List<TermFacetedSearchFormOption> children) {
        return new TermFacetedSearchFormOptionImpl(fieldLabel, fieldValue, value, count, children);
    }

    static TermFacetedSearchFormOption ofTermStats(final TermStats termStats) {
        final long count = firstNonNull(termStats.getProductCount(), 0L);
        return of(termStats.getTerm(), termStats.getTerm(), termStats.getTerm(), count, emptyList());
    }
}
