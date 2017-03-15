package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.TermFacetedSearchFormOption;
import io.sphere.sdk.search.TermFacetResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public interface TermFacetedSearchFormSettingsWithOptions<T> extends TermFacetedSearchFormSettings<T>, FacetedSearchFormSettingsWithOptions<T, TermFacetedSearchFormOption> {

    /**
     * Whether the facet can be displayed or not.
     * @return true if the facet can be displayed, false otherwise
     */
    default boolean isAvailable() {
        return Optional.ofNullable(getThreshold())
                .map(threshold -> getOptions().size() >= threshold)
                .orElse(true);
    }

    /**
     * Obtains the truncated options list according to the defined limit.
     * @return the truncated options if the limit is defined, the whole list otherwise
     */
    default List<TermFacetedSearchFormOption> getLimitedOptions() {
        return Optional.ofNullable(getLimit())
                .map(limit -> getOptions().stream()
                        .limit(limit)
                        .collect(toList()))
                .orElse(getOptions());
    }

    static <T> TermFacetedSearchFormSettingsWithOptions<T> of(final TermFacetedSearchFormSettings<T> settings,
                                                              final List<TermFacetedSearchFormOption> options) {
        return new TermFacetedSearchFormSettingsWithOptionsImpl<>(settings, options);
    }

    /**
     * Generates the facet options according to the facet result and mapper provided.
     * @return the generated facet options
     */
    static <T> TermFacetedSearchFormSettingsWithOptions<T> ofFacetResult(final TermFacetedSearchFormSettings<T> settings,
                                                                      final TermFacetResult facetResult,
                                                                      @Nullable final TermFacetMapper mapper) {
        final List<TermFacetedSearchFormOption> options = Optional.ofNullable(mapper)
                .map(m -> m.apply(facetResult))
                .orElseGet(() -> facetResult.getTerms().stream()
                        .map(TermFacetedSearchFormOption::ofTermStats)
                        .collect(toList()));
        return of(settings, options);
    }
}
