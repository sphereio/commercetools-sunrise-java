package io.sphere.sdk.facets;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.*;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class RangeBucketFacetTest {

    private static final String KEY = "bucket-range";
    private static final List<RangeBucketOption> OPTIONS_THREE = asList(
            RangeBucketOption.of("0", "50", 10L, false),
            RangeBucketOption.of("50", "100", 5L, true),
            RangeBucketOption.of("100", "150", 5L, false)
    );
    private static final TermFacetedSearchSearchModel<ProductProjection> SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().categories().id();
    private static final RangeTermFacetedSearchSearchModel<ProductProjection> RANGE_SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().createdAt();
    private static final RangeFacetResult RESULTS_THREE = RangeFacetResult.of(asList(
            RangeStats.of("0", "50", 10L, 10L, "0", "50", "250", 250.0),
            RangeStats.of("50", "100", 5L, 5L, "50", "100", "300", 300.0),
            RangeStats.of("100", "150", 5L, 5L, "100", "150", "450", 450.0)
    ));
    private static final RangeFacetResult RESULTS_ONE = RangeFacetResult.of(asList(
            RangeStats.of("0", "50", 10L, 10L, "0", "50", "250", 250.0)
    ));
    private static final List<FacetRange> SELECTED_RANGES = asList(
            FacetRange.of(50, 100)
    );

    @Test
    public void getResult(){
        RangeBucketFacet rangeBucketFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeBucketFacet.getFacetResult()).isEqualTo(RESULTS_THREE);
    }

    @Test
    public void isAvailable(){
        RangeBucketFacet rangeBucketFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeBucketFacet.isAvailable()).isTrue();
    }

    @Test
    public void isNotAvailable(){
        RangeBucketFacet rangeBucketFacet = rangeFacet().facetResult(RESULTS_ONE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeBucketFacet.isAvailable()).isFalse();
    }

    @Test
    public void getSelectedRanges() {
        RangeBucketFacet rangeBucketFacet = rangeFacet().facetResult(RESULTS_ONE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeBucketFacet.getSelectedRanges()).containsExactlyElementsOf(SELECTED_RANGES);
    }

    @Test
    public void generateOptions(){
        RangeBucketFacet rangeBucketFacet = rangeFacet().facetResult(RESULTS_THREE).selectedRanges(SELECTED_RANGES).build();
        assertThat(rangeBucketFacet.getFacetOptions()).containsExactlyElementsOf(OPTIONS_THREE);
    }

    private RangeBucketFacetBuilder<ProductProjection> rangeFacet() {
        return RangeBucketFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL);
    }

}
