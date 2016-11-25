package io.sphere.sdk.facets;

import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class RangeBucketFacetImpl<T> extends BaseFacet<T> implements RangeBucketFacet<T> {

    private final List<FacetRange> selectedValues;
    @Nullable private final RangeFacetResult facetResult;
    private final List<FacetOption> facetOptions;
//    private final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;

    protected RangeBucketFacetImpl(final String key, final String label, final boolean countHidden, final FacetType type,
                             final FacetedSearchSearchModel<T> searchModel, final List<FacetRange> selectedValues,
                             @Nullable final RangeFacetResult facetResult, RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key, type, countHidden, label, searchModel);
        this.selectedValues = selectedValues;
        this.facetResult = facetResult;
        this.facetOptions = null;
//        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public List<FacetRange> getSelectedRange() {
        return selectedValues;
    }

    @Nullable
    @Override
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Nullable
    @Override
    public FacetOptionMapper getMapper() {
        return mapper;
    }

    @Nullable
    @Override
    public Long getThreshold() {
        return threshold;
    }

    @Nullable
    @Override
    public Long getLimit() {
        return limit;
    }

    @Override
    public FacetedSearchExpression<T> getFacetedSearchExpression() {
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.allRanges();
        } else {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.containsAny(selectedValues);
        }
        return facetedSearchExpr;
    }

    @Override
    public RangeFacet<T> withSearchResult(final PagedSearchResult<T> searchResult) {
        final RangeFacetResult rangeFacetResult = searchResult.getFacetResult(rangeTermFacetedSearchSearchModel.allRanges().facetExpression());
        return RangeFacetBuilder.of(this).facetResult(rangeFacetResult).build();
    }

    @Override
    public RangeFacet<T> withSelectedValues(List<String> selectedValues) {
        return RangeFacetBuilder.of(this).selectedValues(selectedValues).build();
    }

    @Override
    public RangeFacet<T> withLabel(String label) {
        return RangeFacetBuilder.of(this).label(label).build();
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    private static List<FacetOption> initializeOptions(final List<String> selectedValues, @Nullable final RangeFacetResult facetResult,
                                                       @Nullable final FacetOptionMapper mapper) {
        final List<FacetOption> facetOptions = Optional.ofNullable(facetResult)
                .map(result -> result.getRanges().stream()
                        .map(rangeTermStats -> FacetOption.of("From " + rangeTermStats.getMin() + " to " + rangeTermStats.getMax(), rangeTermStats.getCount(), selectedValues.contains(rangeTermStats.getMin()+"-"+rangeTermStats.getMax())))
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
        return Optional.ofNullable(mapper).map(m -> m.apply(facetOptions)).orElse(facetOptions);
    }

    public RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }
}
