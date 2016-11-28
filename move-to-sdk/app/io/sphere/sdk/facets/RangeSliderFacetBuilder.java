package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public class RangeSliderFacetBuilder<T> extends FacetBuilder<RangeSliderFacetBuilder<T>> implements Builder<RangeSliderFacet<T>> {

    private FacetedSearchSearchModel<T> facetedSearchSearchModel;
    @Nullable
    private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;
    private FacetRange selectedRange = null;

    private RangeSliderFacetBuilder(final String key, final FacetedSearchSearchModel<T> facetedSearchSearchModel,
                                    final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.facetedSearchSearchModel = facetedSearchSearchModel;
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public RangeSliderFacet<T> build() {
        return new RangeSliderFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(), facetedSearchSearchModel,
                selectedRange, facetResult, rangeTermFacetedSearchSearchModel);
    }

    public RangeSliderFacetBuilder<T> facetedSearchSearchModel(final FacetedSearchSearchModel<T> facetedSearchSearchModel) {
        this.facetedSearchSearchModel = facetedSearchSearchModel;
        return this;
    }

    public RangeSliderFacetBuilder<T> rangeTermFacetedSearchSearchModel(final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        return this;
    }

    public RangeSliderFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public RangeSliderFacetBuilder<T> selectedRange(@Nullable final FacetRange selectedRange) {
        this.selectedRange = selectedRange;
        return this;
    }

    public FacetedSearchSearchModel<T> getFacetedSearchSearchModel() {
        return facetedSearchSearchModel;
    }

    public RangeTermFacetedSearchSearchModel<T> getRangeTermFacetedSearchSearchModel() {
        return rangeTermFacetedSearchSearchModel;
    }

    @Nullable
    public RangeFacetResult getFacetResult() {
        return facetResult;
    }

    @Nullable
    public FacetRange getSelectedRange() {
        return selectedRange;
    }

    @Override
    protected RangeSliderFacetBuilder<T> getThis() {
        return this;
    }

    public static <T> RangeSliderFacetBuilder<T> of(final String key, final FacetedSearchSearchModel<T> searchModel,
                                                    final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        return new RangeSliderFacetBuilder<>(key, searchModel, rangeTermFacetedSearchSearchModel);
    }

    public static <T> RangeSliderFacetBuilder<T> of(final RangeSliderFacet<T> facet) {
        final RangeSliderFacetBuilder<T> builder = new RangeSliderFacetBuilder<>(facet.getKey(), facet.getFacetedSearchSearchModel(),
                facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.selectedRange = facet.getSelectedRange();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
