package io.sphere.sdk.facets;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.facets.DefaultFacetType.CATEGORY_TREE;
import static io.sphere.sdk.facets.DefaultFacetType.SELECT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class RangeFacetBuilderTest {

    private static final String KEY = "single-range-facet";
    private static final String LABEL = "Select one option";
    private static final RangeTermFacetedSearchSearchModel<ProductProjection> RANGE_SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().createdAt();
    private static final TermFacetedSearchSearchModel<ProductProjection> SEARCH_MODEL = ProductProjectionSearchModel.of().facetedSearch().categories().id();
    private static final RangeFacetResult FACET_RESULT_WITH_THREE_RANGES = RangeFacetResult.of(asList(
            RangeStats.of("0", "10", 30L, null, "0", "10", "300", 300.0),
            RangeStats.of("10", "20", 20L, null, "10", "20", "300", 200.0),
            RangeStats.of("20", "30", 10L, null, "20", "30", "300", 100.0)));
    private static final List<String> SELECTED_VALUE_TWO = singletonList("10-20");
    private static final List<FacetOption> OPTIONS = asList(
            FacetOption.of("From 0 to 10", 30, false),
            FacetOption.of("From 10 to 20", 20, true),
            FacetOption.of("From 20 to 30", 10, false));
    public static final FacetOptionMapper IDENTITY_MAPPER = v -> v;

    @Test
    public void createsInstance() throws Exception {
        final RangeFacet<ProductProjection> facet = RangeFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL)
                .label(LABEL)
                .mapper(IDENTITY_MAPPER)
                .type(CATEGORY_TREE)
                .facetResult(FACET_RESULT_WITH_THREE_RANGES)
                .selectedValues(SELECTED_VALUE_TWO)
                .threshold(3L)
                .limit(10L)
                .build();
        assertThat(facet.getKey()).isEqualTo(KEY);
        assertThat(facet.getType()).isEqualTo(CATEGORY_TREE);
        assertThat(facet.getLabel()).contains(LABEL);
        assertThat(facet.getMapper()).isEqualTo(IDENTITY_MAPPER);
        assertThat(facet.getFacetedSearchSearchModel()).isEqualTo(SEARCH_MODEL);
        assertThat(facet.getFacetResult()).isEqualTo(FACET_RESULT_WITH_THREE_RANGES);
        assertThat(facet.getSelectedValues()).containsExactlyElementsOf(SELECTED_VALUE_TWO);
        assertThat(facet.getThreshold()).isEqualTo(3L);
        assertThat(facet.getLimit()).isEqualTo(10L);
        assertThat(facet.isAvailable()).isTrue();
        assertThat(facet.getAllOptions()).containsExactlyElementsOf(OPTIONS);
        assertThat(facet.getLimitedOptions()).containsExactlyElementsOf(OPTIONS);
    }

    @Test
    public void createsInstanceWithOptionalValues() throws Exception {
        final RangeFacet<ProductProjection> facet = RangeFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL).build();
        assertThat(facet.getKey()).isEqualTo(KEY);
        assertThat(facet.getType()).isEqualTo(SELECT);
        assertThat(facet.getLabel()).isNull();
        assertThat(facet.getFacetedSearchSearchModel()).isEqualTo(SEARCH_MODEL);
        assertThat(facet.getMapper()).isNull();
        assertThat(facet.getFacetResult()).isNull();
        assertThat(facet.getSelectedValues()).isEmpty();
        assertThat(facet.getThreshold()).isEqualTo(1L);
        assertThat(facet.getLimit()).isNull();
        assertThat(facet.isAvailable()).isFalse();
        assertThat(facet.getAllOptions()).isEmpty();
        assertThat(facet.getLimitedOptions()).isEmpty();
    }

    @Test
    public void mapsOptions() throws Exception {
        final CustomSortedFacetOptionMapper mapper = CustomSortedFacetOptionMapper.of(asList("From 10 to 20", "From 20 to 30", "From 0 to 10"));
        final RangeFacet<ProductProjection> facet = RangeFacetBuilder.of(KEY, SEARCH_MODEL, RANGE_SEARCH_MODEL)
                .mapper(mapper)
                .facetResult(FACET_RESULT_WITH_THREE_RANGES)
                .selectedValues(SELECTED_VALUE_TWO)
                .build();
        assertThat(facet.getMapper()).isEqualTo(mapper);
        assertThat(facet.getAllOptions()).containsExactly(OPTIONS.get(1), OPTIONS.get(2), OPTIONS.get(0));
        assertThat(facet.getLimitedOptions()).containsExactly(OPTIONS.get(1), OPTIONS.get(2), OPTIONS.get(0));
    }

}
