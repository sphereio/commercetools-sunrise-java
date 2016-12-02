package io.sphere.sdk.facets;

import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public class SliderRangeFacetImpl<T> extends BaseFacet<T> implements SliderRangeFacet<T> {

    private final FilterRange<String> selectedRange;
    @Nullable private final RangeFacetResult facetResult;
    private final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel;

    protected SliderRangeFacetImpl(final String key, final String label, final boolean countHidden, final FacetType type,
                                   final FilterRange<String> selectedRange, @Nullable final RangeFacetResult facetResult,
                                   final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key, type, countHidden, label);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        this.selectedRange = selectedRange;
        this.facetResult = facetResult;
    }

    @Nullable
    @Override
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Override
    public FilterRange<String> getSelectedRange() {
        return selectedRange;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public FacetedSearchSearchModel<T> getFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }

    @Override
    public FacetedSearchExpression<T> getFacetedSearchExpression() {
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRange != null) {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.isBetween(selectedRange);
        } else {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.allRanges();
        }
        return facetedSearchExpr;
    }

    @Override
    public SliderRangeFacet<T> withSearchResult(PagedSearchResult<T> searchResult) {
        final RangeFacetResult rangeFacetResult = searchResult.getFacetResult(rangeTermFacetedSearchSearchModel.allRanges().facetExpression());
        return SliderRangeFacetBuilder.of(this).facetResult(rangeFacetResult).build();
    }

    @Override
    public Facet<T> withLabel(String label) {
        return SliderRangeFacetBuilder.of(this).label(label).build();
    }

    @Override
    public SliderRangeFacet<T> withSelectedRange(FilterRange<String> selectedRanges) {
        return SliderRangeFacetBuilder.of(this).selectedRange(selectedRanges).build();
    }

    @Override
    public RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }
}
