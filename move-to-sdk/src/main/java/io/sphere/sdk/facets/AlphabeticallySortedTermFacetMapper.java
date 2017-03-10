package io.sphere.sdk.facets;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that sorts the options according to the label alphabetical order.
 */
public class AlphabeticallySortedTermFacetMapper implements TermFacetsMapper2 {

    private AlphabeticallySortedTermFacetMapper() {
    }

    @Override
    public List<FacetOption> apply(final List<FacetOption> facetOptions) {
        return facetOptions.stream()
                .sorted((option1, option2) -> option1.getLabel().compareTo(option2.getLabel()))
                .collect(toList());
    }

    public static AlphabeticallySortedTermFacetMapper of() {
        return new AlphabeticallySortedTermFacetMapper();
    }
}
