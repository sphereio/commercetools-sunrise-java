package io.sphere.sdk.facets;

import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class RangeFacetImpl<T> extends BaseFacet<T> implements RangeFacet<T> {

    private final List<FilterRange<String>> selectedRanges;
    @Nullable private final RangeFacetResult facetResult;
    private final List<RangeOption> facetOptions;
    private final List<FacetRange<String>> initialRanges;
    private final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel;

    protected RangeFacetImpl(final String key, final String label, final boolean countHidden, final FacetType type, final List<FilterRange<String>> selectedRanges,
                                   final List<FacetRange<String>> initialRanges, @Nullable final RangeFacetResult facetResult, RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key, type, countHidden, label);
        this.selectedRanges = selectedRanges;
        this.facetResult = facetResult;
        this.facetOptions = initializeOptions(selectedRanges, facetResult);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        this.initialRanges = initialRanges;
    }

    @Override
    public List<FilterRange<String>> getSelectedRanges() {
        return selectedRanges;
    }

    @Nullable
    @Override
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Override
    public List<FacetRange<String>> getInitialRanges() {
        return initialRanges;
    }

    @Override
    public boolean isAvailable() {
        return facetOptions.size()>1;
    }

    @Override
    public FacetedSearchSearchModel<T> getFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }

    @Override
    public List<RangeOption> getOptions() {
        return facetOptions;
    }

    @Override
    public FacetedSearchExpression<T> getFacetedSearchExpression() {
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRanges.isEmpty()) {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.allRanges();
        } else {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.isBetweenAny(selectedRanges);
        }
        return buildRangeFacetedSearchExpression(facetedSearchExpr);
    }

    private RangeFacetedSearchExpression<T> buildRangeFacetedSearchExpression(final FacetedSearchExpression<T> facetedSearchExpr) {
        final List<FacetRange<String>> facetRanges = initialRanges;
        final RangeTermFacetSearchModel<T, String> searchModel = extractSearchModel(facetedSearchExpr);
        return RangeFacetedSearchExpression.of(searchModel.onlyRange(facetRanges), facetedSearchExpr.filterExpressions());
    }

    private RangeTermFacetSearchModel<T, String> extractSearchModel(final FacetedSearchExpression<T> facetedSearchExpr) {
        final String attributePath = facetedSearchExpr.facetExpression().attributePath();
        final RangeTermFacetSearchModel<T, String> searchModel = RangeTermFacetSearchModel.of(attributePath, TypeSerializer.ofString());
        return searchModel.withAlias(facetedSearchExpr.facetExpression().alias());
    }

    @Override
    public RangeFacet<T> withSearchResult(final PagedSearchResult<T> searchResult) {
        final RangeFacetResult rangeFacetResult = searchResult.getFacetResult(rangeTermFacetedSearchSearchModel.allRanges().facetExpression());
        return RangeFacetBuilder.of(this).facetResult(rangeFacetResult).build();
    }

    @Override
    public RangeFacet<T> withSelectedRanges(List<FilterRange<String>> selectedRanges) {
        return RangeFacetBuilder.of(this).selectedRanges(selectedRanges).build();
    }

    @Override
    public RangeFacet<T> withInitialRanges(List<FacetRange<String>> initialRanges) {
        return RangeFacetBuilder.of(this).initialRanges(initialRanges).build();
    }

    @Override
    public RangeFacet<T> withLabel(String label) {
        return RangeFacetBuilder.of(this).label(label).build();
    }

    /**
     * Generates the facet options according to the facet result and selected values provided.
     * @return the generated facet options
     */
    private List<RangeOption> initializeOptions(final List<FilterRange<String>> selectedValue, @Nullable final RangeFacetResult facetResult) {
        return Optional.ofNullable(facetResult)
                .map(result -> result.getRanges().stream()
                        .map(rangeTermStats -> RangeOption.of(rangeTermStats.getLowerEndpoint(), rangeTermStats.getUpperEndpoint(), rangeTermStats.getCount() ,checkIfSelected(rangeTermStats, selectedValue)))
                        .filter(range -> range.getCount() > 0)
                        .collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    private Boolean checkIfSelected(RangeStats rangeTermStats, List<FilterRange<String>> selectedValue) {
        return selectedValue.contains(FilterRange.of(rangeTermStats.getLowerEndpoint(), rangeTermStats.getUpperEndpoint()));
    }

    @Override
    public RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }

}
