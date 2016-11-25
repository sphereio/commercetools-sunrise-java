package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

public final class RangeBucketFacetBuilder<T> extends FacetBuilder<RangeBucketFacetBuilder<T>> implements Builder<RangeBucketFacet<T>> {

    private FacetedSearchSearchModel<T> facetedSearchSearchModel;
    @Nullable private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;
    private List<FacetRange> selectedRanges = null;

    private RangeBucketFacetBuilder(final String key, final FacetedSearchSearchModel<T> facetedSearchSearchModel,
                              final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.facetedSearchSearchModel = facetedSearchSearchModel;
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public RangeBucketFacet<T> build() {
        return new RangeBucketFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(), facetedSearchSearchModel,
                selectedRanges, facetResult, rangeTermFacetedSearchSearchModel);
    }

    public RangeBucketFacetBuilder<T> facetedSearchSearchModel(final FacetedSearchSearchModel<T> facetedSearchSearchModel) {
        this.facetedSearchSearchModel = facetedSearchSearchModel;
        return this;
    }

    public RangeBucketFacetBuilder<T> rangeTermFacetedSearchSearchModel(final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        return this;
    }

    public RangeBucketFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public RangeBucketFacetBuilder<T> selectedRanges(@Nullable final List<FacetRange> selectedRanges) {
        this.selectedRanges = selectedRanges;
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
    public List<FacetRange> getSelectedRanges() {
        return selectedRanges;
    }

    @Override
    protected RangeBucketFacetBuilder<T> getThis() {
        return this;
    }

    public static <T> RangeBucketFacetBuilder<T> of(final String key, final FacetedSearchSearchModel<T> searchModel,
                                              final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        return new RangeBucketFacetBuilder<>(key, searchModel, rangeTermFacetedSearchSearchModel);
    }

    public static <T> RangeBucketFacetBuilder<T> of(final RangeBucketFacet<T> facet) {
        final RangeBucketFacetBuilder<T> builder = new RangeBucketFacetBuilder<>(facet.getKey(), facet.getFacetedSearchSearchModel(),
                facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.selectedRanges = facet.getSelectedRanges();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
