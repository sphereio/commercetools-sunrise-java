package io.sphere.sdk.facets;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;

import javax.annotation.Nullable;
import java.util.List;

public final class RangeFacetBuilder<T> extends FacetBuilder<RangeFacetBuilder<T>> implements Builder<RangeFacet<T>> {

    @Nullable private RangeFacetResult facetResult = null;
    private RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel;
    private List<FilterRange<String>> selectedRanges = null;
    private List<FacetRange<String>> initialRanges = null;

    private RangeFacetBuilder(final String key, final RangeTermFacetedSearchSearchModel rangeTermFacetedSearchSearchModel) {
        super(key);
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
    }

    @Override
    public RangeFacet<T> build() {
        return new RangeFacetImpl<>(getKey(), getLabel(), isCountHidden(), getType(), selectedRanges, initialRanges,
                facetResult, rangeTermFacetedSearchSearchModel);
    }

    public RangeFacetBuilder<T> rangeTermFacetedSearchSearchModel(final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        this.rangeTermFacetedSearchSearchModel = rangeTermFacetedSearchSearchModel;
        return this;
    }

    public RangeFacetBuilder<T> facetResult(@Nullable final RangeFacetResult facetResult) {
        this.facetResult = facetResult;
        return this;
    }

    public RangeFacetBuilder<T> selectedRanges(@Nullable final List<FilterRange<String>> selectedRanges) {
        this.selectedRanges = selectedRanges;
        return this;
    }

    public RangeFacetBuilder<T> initialRanges(@Nullable final List<FacetRange<String>> initialRanges) {
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
    protected RangeFacetBuilder<T> getThis() {
        return this;
    }

    public static <T> RangeFacetBuilder<T> of(final String key,
                                              final RangeTermFacetedSearchSearchModel<T> rangeTermFacetedSearchSearchModel) {
        return new RangeFacetBuilder<>(key, rangeTermFacetedSearchSearchModel);
    }

    public static <T> RangeFacetBuilder<T> of(final RangeFacet<T> facet) {
        final RangeFacetBuilder<T> builder = new RangeFacetBuilder<>(facet.getKey(),
                facet.getRangeFacetedSearchSearchModel());
        builder.type = facet.getType();
        builder.label = facet.getLabel();
        builder.countHidden = facet.isCountHidden();
        builder.selectedRanges = facet.getSelectedRanges();
        builder.facetResult = facet.getFacetResult();
        return builder;
    }
}
