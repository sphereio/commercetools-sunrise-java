package io.sphere.sdk.facets;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RangeFacetTest {
    private static final RangeFacetResult FACET_RESULT_WITH_THREE_RANGES = RangeFacetResult.of(asList(
            RangeStats.of("0", "10", 30L, null, "0", "10", "300", 300.0),
            RangeStats.of("10", "20", 20L, null, "10", "20", "300", 200.0),
            RangeStats.of("20", "30", 10L, null, "20", "30", "300", 100.0)));
    private static final List<String> SELECTED_VALUE_TWO = singletonList("10-20");
    private static final List<FacetOption> OPTIONS = asList(
            FacetOption.of("From 0 to 10", 30, false),
            FacetOption.of("From 10 to 20", 20, true),
            FacetOption.of("From 20 to 30", 10, false));
    private static final RangeTermFacetedSearchSearchModel<ProductProjection> RANGE_SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().createdAt();
    private static final TermFacetedSearchSearchModel<ProductProjection> SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().categories().id();


    @Test
    public void canBeDisplayedIfOverThreshold() throws Exception {
        final RangeFacet<ProductProjection> facet = rangeFacetWithThreeOptions().threshold(3L).build();
        assertThat(facet.isAvailable()).isTrue();
    }

    @Test
    public void canNotBeDisplayedIfBelowThreshold() throws Exception {
        final RangeFacet<ProductProjection> facet = rangeFacetWithThreeOptions().threshold(4L).build();
        assertThat(facet.isAvailable()).isFalse();
    }

    @Test
    public void throwsExceptionOnWrongThresholdAndLimit() throws Exception {
        final RangeFacetBuilder<ProductProjection> builder = rangeFacetWithThreeOptions().threshold(10L);
        assertThatThrownBy(() -> {
            builder.limit(10L).build();
            builder.limit(3L).build();
        }).isExactlyInstanceOf(InvalidSelectFacetConstraintsException.class)
                .hasMessageContaining("Threshold: 10")
                .hasMessageContaining("Limit: 3");
    }

    @Test
    public void optionsListIsTruncatedIfOverLimit() throws Exception {
        final RangeFacet<ProductProjection> facet = rangeFacetWithThreeOptions()
                .selectedValues(SELECTED_VALUE_TWO)
                .limit(2L)
                .build();
        assertThat(facet.getLimitedOptions()).containsExactlyElementsOf(asList(OPTIONS.get(0), OPTIONS.get(1)));
        assertThat(facet.getAllOptions()).containsExactlyElementsOf(OPTIONS);
    }

    @Test
    public void optionsListIsNotTruncatedIfBelowLimit() throws Exception {
        final RangeFacet<ProductProjection> facet = rangeFacetWithThreeOptions()
                .selectedValues(SELECTED_VALUE_TWO)
                .limit(3L)
                .build();
        assertThat(facet.getLimitedOptions()).containsExactlyElementsOf(OPTIONS);
        assertThat(facet.getAllOptions()).containsExactlyElementsOf(OPTIONS);
    }

    private RangeFacetBuilder<ProductProjection> rangeFacetWithThreeOptions() {
        return rangeFacet().facetResult(FACET_RESULT_WITH_THREE_RANGES);
    }

    private RangeFacetBuilder<ProductProjection> rangeFacet() {
        return RangeFacetBuilder.of("foo", SEARCH_MODEL, RANGE_SEARCH_MODEL);
    }
}
