package com.commercetools.sunrise.search.facetedsearch;

import java.util.List;

final class TermFacetedSearchFormOptionImpl extends FacetedSearchFormOptionImpl implements TermFacetedSearchFormOption {

    private final List<TermFacetedSearchFormOption> children;

    TermFacetedSearchFormOptionImpl(final String fieldLabel, final String fieldValue, final String value,
                                    final long count, final List<TermFacetedSearchFormOption> children) {
        super(fieldLabel, fieldValue, value, count);
        this.children = children;
    }

    @Override
    public List<TermFacetedSearchFormOption> getChildren() {
        return children;
    }
}
