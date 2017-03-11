package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettingsWithOptions;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

interface FacetedSearchFormSettingsWithOptions<T extends FacetedSearchFormOption> extends FacetedSearchFormSettings, FormSettingsWithOptions<T, String> {

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
    default List<T> getLimitedOptions() {
        return Optional.ofNullable(getLimit())
                .map(limit -> getOptions().stream()
                        .limit(limit)
                        .collect(toList()))
                .orElse(getOptions());
    }
}
