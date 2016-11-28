package io.sphere.sdk.facets;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RangeSliderFacetTest {

    private static final String KEY = "slider-range";
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
    private static final FacetRange SELECTED_RANGE = FacetRange.of(50, 100);

    @Test
    public void getResult(){
        RangeSliderFacet rangeSliderFacet = rangeFacet().facetResult(RESULTS_THREE).build();
        assertThat(rangeSliderFacet.getFacetResult()).isEqualTo(RESULTS_THREE);
    }

    @Test
    public void getSelectedRange() {
        RangeSliderFacet rangeSliderFacet = rangeFacet().facetResult(RESULTS_ONE).selectedRange(SELECTED_RANGE).build();
        assertThat(rangeSliderFacet.getSelectedRange()).isEqualTo(SELECTED_RANGE);
    }

    private RangeSliderFacetBuilder<ProductProjection> rangeFacet() {
        return RangeSliderFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL);
    }

}
