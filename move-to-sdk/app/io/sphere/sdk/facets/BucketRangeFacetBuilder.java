package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

public final class BucketRangeFacetBuilder<T> extends FacetBuilder<BucketRangeFacetBuilder<T>> implements Builder<BucketRangeFacet<T>> {

    @Nullable private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel;
    private List<FilterRange<String>> selectedRanges = null;
    private List<FacetRange<String>> initialRanges = null;

    private BucketRangeFacetBuilder(final String key, final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public BucketRangeFacet<T> build() {
        return new BucketRangeFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(), selectedRanges, initialRanges,
                facetResult, rangeTermFacetedSearchSearchModel);
    }

    public BucketRangeFacetBuilder<T> rangeTermFacetedSearchSearchModel(final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        return this;
    }

    public BucketRangeFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public BucketRangeFacetBuilder<T> selectedRanges(@Nullable final List<FilterRange<String>> selectedRanges) {
        this.selectedRanges = selectedRanges;
        return this;
    }

    public BucketRangeFacetBuilder<T> initialRanges(@Nullable final List<FacetRange<String>> initialRanges) {
        this.initialRanges = initialRanges;
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
    public List<FilterRange<String>> getSelectedRanges() {
        return selectedRanges;
    }

    @Override
    protected BucketRangeFacetBuilder<T> getThis() {
        return this;
    }

    public static <T> BucketRangeFacetBuilder<T> of(final String key,
                                              final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        return new BucketRangeFacetBuilder<>(key, rangeTermFacetedSearchSearchModel);
    }

    public static <T> BucketRangeFacetBuilder<T> of(final BucketRangeFacet<T> facet) {
        final BucketRangeFacetBuilder<T> builder = new BucketRangeFacetBuilder<>(facet.getKey(),
                facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.selectedRanges = facet.getSelectedRanges();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
