package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.facets.FacetOption;
import io.sphere.sdk.search.TermFacetResult;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class CategoryTermFacetedSearchFormSettingsWithOptionsImpl extends FacetedSearchFormSettingsWithOptionsImpl<TermFacetedSearchFormOption> implements TermFacetedSearchFormSettingsWithOptions {

    CategoryTermFacetedSearchFormSettingsWithOptionsImpl(final FacetedSearchFormSettings settings, final List<TermFacetedSearchFormOption> options) {
        super(settings, options);
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    private static List<TermFacetedSearchFormOption> initializeOptions(final FacetedSearchFormSettings settings, @Nullable final TermFacetResult facetResult,
                                                                       final List<Category> selectedCategories) {
        final List<String> selectedValues = findSelectedValues(selectedCategories);
        final List<TermFacetedSearchFormOption> options = Optional.ofNullable(facetResult)
                .map(result -> result.getTerms().stream()
                        .map(termStats ->  new TermFacetedSearchFormOptionImpl() FacetOption.ofTermStats(termStats, selectedValues))
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
        return Optional.ofNullable(mapper).map(m -> m.apply(options)).orElse(options);
    }

    private static List<String> findSelectedValues(final List<Category> selectedCategories) {
        return selectedCategories.stream()
                .map(Category::getId)
                .distinct()
                .collect(toList());
    }
}
