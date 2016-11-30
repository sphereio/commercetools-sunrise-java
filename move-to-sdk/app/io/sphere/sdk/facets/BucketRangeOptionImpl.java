package io.sphere.sdk.facets;

import io.sphere.sdk.models.Base;

public class BucketRangeOptionImpl extends Base implements BucketRangeOption{

    private final String lowerEndpoint;
    private final String upperEndpoint;
    private final Long count;
    private final Boolean selected;

    public BucketRangeOptionImpl(String lowerEndpoint, String upperEndpoint, Long count, Boolean selected) {
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
    public Boolean isSelected() {
        return selected;
    }

    @Override
    public BucketRangeOption withLowerEndpoint(final String lowerEndpoint) {
        return new BucketRangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public BucketRangeOption withUpperEndpoint(final String upperEndpoint) {
        return new BucketRangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public BucketRangeOption withCount(final Long count) {
        return new BucketRangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public BucketRangeOption withSelected(final Boolean selected) {
        return new BucketRangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}
