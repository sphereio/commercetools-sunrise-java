package io.sphere.sdk.facets;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.facets.DefaultFacetType.BUCKET_RANGE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class RangeBucketFacetBuilderTest {
    private static final String KEY = "bucket-range";
    private static final String LABEL = "Select one option";
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
    private static final List<FacetRange> SELECTED_RANGES = asList(
            FacetRange.of(50, 100)
    );

    @Test
    public void createsInstance() throws Exception {
        final RangeBucketFacet<ProductProjection> facet = RangeBucketFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL)
                .label(LABEL)
                .type(BUCKET_RANGE)
                .facetResult(RESULTS_THREE)
                .selectedRanges(SELECTED_RANGES)
                .build();

        assertThat(facet.getKey()).isEqualTo(KEY);
        assertThat(facet.getType()).isEqualTo(BUCKET_RANGE);
        assertThat(facet.getLabel()).contains(LABEL);
        assertThat(facet.getFacetedSearchSearchModel()).isEqualTo(SEARCH_MODEL);
        assertThat(facet.getRangeFacetedSearchSearchModel()).isEqualTo(RANGE_SEARCH_MODEL);
        assertThat(facet.getFacetResult()).isEqualTo(RESULTS_THREE);
        assertThat(facet.getSelectedRanges()).containsExactlyElementsOf(SELECTED_RANGES);
        assertThat(facet.getFacetOptions()).containsExactlyElementsOf(OPTIONS_THREE);
    }
}
