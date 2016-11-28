package io.sphere.sdk.facets;

import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

public class RangeSliderFacetImpl<T> extends BaseFacet<T> implements RangeSliderFacet<T> {

    private final FacetRange selectedRange;
    @Nullable private final RangeFacetResult facetResult;
    private final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel;

    protected RangeSliderFacetImpl(final String key, final String label, final boolean countHidden, final FacetType type,
                                    final FacetedSearchSearchModel<T> searchModel,final FacetRange selectedRange, @Nullable final RangeFacetResult facetResult,
                                   final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key, type, countHidden, label, searchModel);
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
    public FacetRange getSelectedRange() {
        return selectedRange;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public List<String> getSelectedValues() {
        return null;
    }

    @Override
    public FacetedSearchExpression<T> getFacetedSearchExpression() {
        final FacetedSearchExpression<T> facetedSearchExpr;
        if (selectedRange != null) {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.isBetween(FilterRange.of(selectedRange.lowerEndpoint().toString(), selectedRange.upperEndpoint().toString()));
        } else {
            facetedSearchExpr = rangeTermFacetedSearchSearchModel.allRanges();
        }
        return facetedSearchExpr;
    }

    @Override
    public RangeSliderFacet<T> withSearchResult(PagedSearchResult<T> searchResult) {
        final RangeFacetResult rangeFacetResult = searchResult.getFacetResult(rangeTermFacetedSearchSearchModel.allRanges().facetExpression());
        return RangeSliderFacetBuilder.of(this).facetResult(rangeFacetResult).build();
    }

    @Override
    public Facet<T> withSelectedValues(List<String> selectedValues) {
        return RangeSliderFacetBuilder.of(this).selectedValues(selectedValues).build();    }

    @Override
    public Facet<T> withLabel(String label) {
        return RangeSliderFacetBuilder.of(this).label(label).build();
    }

    @Override
    public RangeSliderFacet<T> withSelectedRange(FacetRange selectedRanges) {
        return RangeSliderFacetBuilder.of(this).selectedRange(selectedRanges).build();
    }

    @Override
    public RangeTermFacetedSearchSearchModel<T> getRangeFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }
}
