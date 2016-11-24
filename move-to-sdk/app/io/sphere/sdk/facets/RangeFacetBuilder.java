package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;

public final class RangeFacetBuilder<T> extends FacetBuilder<RangeFacetBuilder<T>> implements Builder<RangeFacet<T>> {

    private FacetedSearchSearchModel<T> facetedSearchSearchModel;
    @Nullable private Long threshold = 1L;
    @Nullable private Long limit = null;
    @Nullable private FacetOptionMapper mapper = null;
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

    public RangeFacetBuilder<T> threshold(@Nullable final Long threshold) {
        this.threshold = threshold;
        return this;
    }

    public RangeFacetBuilder<T> mapper(@Nullable final FacetOptionMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public RangeFacetBuilder<T> limit(@Nullable final Long limit) {
        this.limit = limit;
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
