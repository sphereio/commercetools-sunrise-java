package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public final class RangeBucketFacetBuilder<T> extends FacetBuilder<RangeBucketFacetBuilder<T>> implements Builder<RangeBucketFacet<T>> {

    private FacetedSearchSearchModel<T> facetedSearchSearchModel;
    @Nullable private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel;

    private RangeFacetBuilder(final String key, final FacetedSearchSearchModel<T> facetedSearchSearchModel,
                              final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.facetedSearchSearchModel = facetedSearchSearchModel;
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    protected RangeFacetBuilder<T> getThis() {
        return this;
    }

    @Override
    public RangeFacet<T> build() {
        return new RangeFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(), facetedSearchSearchModel,
                selectedValues, facetResult, threshold, limit, mapper, rangeTermFacetedSearchSearchModel);
    }

    public static <T> RangeFacetBuilder<T> of(final String key, final FacetedSearchSearchModel<T> searchModel,
                                              final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        return new RangeFacetBuilder<>(key, searchModel, rangeTermFacetedSearchSearchModel);
    }

    public RangeFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public static <T> RangeFacetBuilder<T> of(final RangeFacet<T> facet) {
        final RangeFacetBuilder<T> builder = new RangeFacetBuilder<>(facet.getKey(), facet.getFacetedSearchSearchModel(),
                facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.mapper = facet.getMapper();
        builder.threshold = facet.getThreshold();
        builder.limit = facet.getLimit();
        builder.selectedValues = facet.getSelectedValues();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
