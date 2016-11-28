package io.sphere.sdk.facets;

import io.sphere.sdk.models.Base;

public class RangeBucketOptionImpl extends Base implements RangeBucketOption{

    private final String lowerEndpoint;
    private final String upperEndpoint;
    private final Long count;
    private final Boolean selected;

    public RangeBucketOptionImpl(String lowerEndpoint, String upperEndpoint, Long count, Boolean selected) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.count = count;
        this.selected = selected;
    }

    @Override
    public String getLowerEndpoint() {
        return lowerEndpoint;
    }

    @Override
    public String getUpperEndpoint() {
        return upperEndpoint;
    }

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public Boolean getSelected() {
        return selected;
    }

    @Override
    public RangeBucketOption withLowerEndpoint(final String lowerEndpoint) {
        return new RangeBucketOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeBucketOption withUpperEndpoint(final String upperEndpoint) {
        return new RangeBucketOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeBucketOption withCount(final Long count) {
        return new RangeBucketOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeBucketOption withSelected(final Boolean selected) {
        return new RangeBucketOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}
