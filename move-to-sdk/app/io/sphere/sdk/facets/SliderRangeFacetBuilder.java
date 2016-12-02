package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public class SliderRangeFacetBuilder<T> extends FacetBuilder<SliderRangeFacetBuilder<T>> implements Builder<SliderRangeFacet<T>> {

    @Nullable
    private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;
    private FilterRange<String> selectedRange = null;

    private SliderRangeFacetBuilder(final String key, final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public SliderRangeFacet<T> build() {
        return new SliderRangeFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(),
                selectedRange, facetResult, rangeTermFacetedSearchSearchModel);
    }

    public SliderRangeFacetBuilder<T> rangeTermFacetedSearchSearchModel(final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        return this;
    }

    public SliderRangeFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public SliderRangeFacetBuilder<T> selectedRange(@Nullable final FilterRange<String> selectedRange) {
        this.selectedRange = selectedRange;
        return this;
    }

    public RangeTermFacetedSearchSearchModel<T> getRangeTermFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }

    @Nullable
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Nullable
    public FilterRange<String> getSelectedRange() {
        return selectedRange;
    }

    @Override
    protected SliderRangeFacetBuilder<T> getThis() {
        return this;
    }

    public static <T> SliderRangeFacetBuilder<T> of(final String key, final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        return new SliderRangeFacetBuilder<>(key, rangeTermFacetedSearchSearchModel);
    }

    public static <T> SliderRangeFacetBuilder<T> of(final SliderRangeFacet<T> facet) {
        final SliderRangeFacetBuilder<T> builder = new SliderRangeFacetBuilder<>(facet.getKey(), facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.selectedRange = facet.getSelectedRange();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
