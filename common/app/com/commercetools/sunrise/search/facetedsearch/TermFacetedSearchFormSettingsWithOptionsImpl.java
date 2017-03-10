package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.facets.FacetOption;
import io.sphere.sdk.facets.TermFacetsMapper2;
import io.sphere.sdk.search.TermFacetResult;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static java.util.stream.Collectors.toList;

class TermFacetedSearchFormSettingsWithOptionsImpl extends FacetedSearchFormSettingsWithOptionsImpl<TermFacetedSearchFormOption> implements TermFacetedSearchFormSettingsWithOptions {

    TermFacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<TermFacetedSearchFormOption> options) {
        super(settings, options);
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    private static List<FacetOption> initializeOptions(final List<String> selectedValues, final TermFacetResult facetResult,
                                                       @Nullable final TermFacetResultMapper mapper) {
        facetResult.getTerms().stream()
                .map(termStats -> TermFacetedSearchFormOption.ofTermStats(termStats, selectedValues))
                .collect(toList()))
                .orElseGet(Collections::emptyList);
        return Optional.ofNullable(mapper)
                .map(m -> m.apply(facetResult))
                .orElseGet(() -> TermFacetedSearchFormOption.of());
    }

    protected List<String> findSelectedValues(final Http.Request httpRequest) {
        return findAllSelectedValuesFromQueryString(getFieldName(), httpRequest);
    }
}
