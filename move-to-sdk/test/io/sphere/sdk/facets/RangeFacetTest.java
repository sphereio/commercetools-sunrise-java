package io.sphere.sdk.facets;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.*;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class RangeFacetTest {

    private static final String KEY = "range-facet";
    private static final List<RangeOption> OPTIONS_THREE = asList(
            RangeOption.of("0", "50", 10L, false),
            RangeOption.of("10", "20", 5L, true),
            RangeOption.of("20", "30", 5L, false)
    );
    private static final RangeTermFacetedSearchSearchModel<ProductProjection> RANGE_SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().createdAt();
    private static final RangeFacetResult RESULTS_THREE = RangeFacetResult.of(asList(
            RangeStats.of("0", "50", 10L, 10L, "0", "50", "250", 250.0),
            RangeStats.of("10", "20", 5L, 5L, "50", "100", "300", 300.0),
            RangeStats.of("20", "30", 5L, 5L, "100", "150", "450", 450.0)
    ));
    private static final RangeFacetResult RESULTS_ONE = RangeFacetResult.of(asList(
            RangeStats.of("0", "50", 10L, 10L, "0", "50", "250", 250.0)
    ));
    private static final List<FilterRange<String>> SELECTED_RANGES = asList(
            FilterRange.of("10", "20")
    );

    @Test
    public void getResult(){
        RangeFacet rangeFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeFacet.getFacetResult()).isEqualTo(RESULTS_THREE);
    }

    @Test
    public void isAvailable(){
        RangeFacet rangeFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeFacet.isAvailable()).isTrue();
    }

    @Test
    public void isNotAvailable(){
        RangeFacet rangeFacet = rangeFacet().facetResult(RESULTS_ONE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeFacet.isAvailable()).isFalse();
    }

    @Test
    public void getSelectedRanges() {
        RangeFacet rangeFacet = rangeFacet().facetResult(RESULTS_ONE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeFacet.getSelectedRanges()).containsExactlyElementsOf(SELECTED_RANGES);
    }

    @Test
    public void generateOptions(){
        RangeFacet rangeFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeFacet.getOptions()).containsExactlyElementsOf(OPTIONS_THREE);
    }

    private RangeFacetBuilder<ProductProjection> rangeFacet() {
        return RangeFacetBuilder.of(KEY, RANGE_SEARCH_MODEL);
    }

}
