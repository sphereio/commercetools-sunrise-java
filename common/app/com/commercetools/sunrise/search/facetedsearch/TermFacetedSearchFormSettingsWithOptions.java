package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapper;
import io.sphere.sdk.search.TermFacetResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public interface TermFacetedSearchFormSettingsWithOptions extends FacetedSearchFormSettingsWithOptions<TermFacetedSearchFormOption> {

    static TermFacetedSearchFormSettingsWithOptions of(final FacetedSearchFormSettings settings, final List<TermFacetedSearchFormOption> options) {
        return new TermFacetedSearchFormSettingsWithOptionsImpl(settings, options);
    }

    /**
     * Generates the facet options according to the facet result and mapper provided.
     * @return the generated facet options
     */
    static TermFacetedSearchFormSettingsWithOptions of(final FacetedSearchFormSettings settings, final TermFacetResult facetResult,
                                                       @Nullable final TermFacetMapper mapper) {
        final List<TermFacetedSearchFormOption> options = Optional.ofNullable(mapper)
                .map(m -> m.apply(facetResult))
                .orElseGet(() -> facetResult.getTerms().stream()
                        .map(TermFacetedSearchFormOption::ofTermStats)
                        .collect(toList()));
        return of(settings, options);
    }
}
