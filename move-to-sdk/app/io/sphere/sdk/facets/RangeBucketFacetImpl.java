package io.sphere.sdk.facets;

import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class RangeBucketFacetImpl<T> extends BaseFacet<T> implements RangeBucketFacet<T> {

    private final List<FacetRange> selectedRanges;
    @Nullable private final RangeFacetResult facetResult;
    private final List<RangeBucketOption> facetOptions;
    private final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;

    protected RangeBucketFacetImpl(final String key, final String label, final boolean countHidden, final FacetType type,
                             final FacetedSearchSearchModel<T> searchModel, final List<FacetRange> selectedRanges,
                             @Nullable final RangeFacetResult facetResult, RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key, type, countHidden, label, searchModel);
        this.selectedRanges = selectedRanges;
        this.facetResult = facetResult;
        this.facetOptions = initializeOptions(selectedRanges, facetResult);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public List<FacetRange> getSelectedRanges() {
        return selectedRanges;
    }

    @Nullable
    @Override
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Override
    public boolean isAvailable() {
        return facetOptions.size()>1;
    }

    @Override
    public List<String> getSelectedValues() {
        return null;
    }

    @Override
    public FacetedSearchExpression<T> getFacetedSearchExpression() {
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRanges.isEmpty()) {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.allRanges();
        } else {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.containsAny(selectedRanges);
        }
        return facetedSearchExpr;
    }

    @Override
    public RangeBucketFacet<T> withSearchResult(final PagedSearchResult<T> searchResult) {
        final RangeFacetResult rangeFacetResult = searchResult.getFacetResult(rangeTermFacetedSearchSearchModel.allRanges().facetExpression());
        return RangeBucketFacetBuilder.of(this).facetResult(rangeFacetResult).build();
    }

    @Override
    public RangeBucketFacet<T> withSelectedRanges(List<FacetRange> selectedRanges) {
        return RangeBucketFacetBuilder.of(this).selectedRanges(selectedRanges).build();
    }

    @Override
    public RangeBucketFacet<T> withSelectedValues(List<String> selectedValues) {
        return RangeBucketFacetBuilder.of(this).selectedValues(selectedValues).build();
    }

    @Override
    public RangeBucketFacet<T> withLabel(String label) {
        return RangeBucketFacetBuilder.of(this).label(label).build();
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    private List<RangeBucketOption> initializeOptions(final List<FacetRange> selectedValue, @Nullable final RangeFacetResult facetResult) {
        return Optional.ofNullable(facetResult)
                .map(result -> result.getRanges().stream()
                        .map(rangeTermStats -> RangeBucketOption.of(rangeTermStats.getLowerEndpoint(), rangeTermStats.getUpperEndpoint(), rangeTermStats.getCount() ,checkIfSelected(rangeTermStats, selectedValue)))
                        .filter(range -> range.getCount() > 0)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    private Boolean checkIfSelected(RangeStats rangeTermStats, List<FacetRange> selectedValue) {
        return selectedValue.contains(FacetRange.of(rangeTermStats.getLowerEndpoint(), rangeTermStats.getUpperEndpoint()));
    }

    public RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }


}
