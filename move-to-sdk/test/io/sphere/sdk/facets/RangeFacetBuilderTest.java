package io.sphere.sdk.facets;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.*;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class RangeFacetBuilderTest {
    private static final String KEY = "range-facet";
    private static final String LABEL = "Select one option";
    private static final List<RangeOption> OPTIONS_THREE = asList(
            RangeOption.of("0", "50", 10L, false),
            RangeOption.of("50", "100", 5L, true),
            RangeOption.of("100", "200", 5L, false)
    );
    private static final TermFacetedSearchSearchModel<ProductProjection> SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().categories().id();
    private static final RangeTermFacetedSearchSearchModel<ProductProjection> RANGE_SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().createdAt();
    private static final RangeFacetResult RESULTS_THREE = RangeFacetResult.of(asList(
            RangeStats.of("0", "50", 10L, 10L, "0", "50", "250", 250.0),
            RangeStats.of("50", "100", 5L, 5L, "50", "100", "300", 300.0),
            RangeStats.of("100", "200", 5L, 5L, "100", "200", "450", 450.0)
    ));
    private static final List<FilterRange<String>> SELECTED_RANGES = asList(
            FilterRange.of("0", "50")
    );

    @Test
    public void createsInstance() throws Exception {
        final RangeFacet facet = RangeFacetBuilder.of(KEY, RANGE_SEARCH_MODEL)
                .label(LABEL)
                .facetResult(RESULTS_THREE)
                .selectedRanges(SELECTED_RANGES)
                .build();

        assertThat(facet.getKey()).isEqualTo(KEY);
        assertThat(facet.getLabel()).contains(LABEL);
        assertThat(facet.getFacetedSearchSearchModel()).isEqualTo(RANGE_SEARCH_MODEL);
        assertThat(facet.getFacetResult()).isEqualTo(RESULTS_THREE);
        assertThat(facet.getSelectedRanges()).containsExactlyElementsOf(SELECTED_RANGES);
        assertThat(facet.getOptions()).containsExactlyElementsOf(OPTIONS_THREE);
    }
}
