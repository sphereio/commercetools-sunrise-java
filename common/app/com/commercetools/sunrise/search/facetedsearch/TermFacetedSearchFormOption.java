package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.facets.FacetOption;
import io.sphere.sdk.search.TermStats;

import java.util.List;

import static java.util.Collections.emptyList;

public interface TermFacetedSearchFormOption extends FacetedSearchFormOption {

    /**
     * Gets the children options of this facet option.
     * @return the children options
     */
    List<TermFacetedSearchFormOption> getChildren();

    static TermFacetedSearchFormOption of(final String fieldLabel, final String fieldValue, final List<String> value,
                                          final boolean isDefault, final long count, final List<TermFacetedSearchFormOption> children) {
        return new TermFacetedSearchFormOptionImpl(fieldLabel, fieldValue, value, isDefault, count, children);
    }

    static TermFacetedSearchFormOption ofTermStats(final TermStats termStats) {
        return of(termStats.getTerm(), termStats.getTerm(), termStats.getCount(), emptyList());
    }
}
