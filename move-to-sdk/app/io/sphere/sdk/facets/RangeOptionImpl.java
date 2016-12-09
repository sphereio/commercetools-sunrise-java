package io.sphere.sdk.facets;

import io.sphere.sdk.models.Base;

public class RangeOptionImpl extends Base implements RangeOption{

    private final String lowerEndpoint;
    private final String upperEndpoint;
    private final Long count;
    private final Boolean selected;

    public RangeOptionImpl(String lowerEndpoint, String upperEndpoint, Long count, Boolean selected) {
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
    public RangeOption withLowerEndpoint(final String lowerEndpoint) {
        return new RangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeOption withUpperEndpoint(final String upperEndpoint) {
        return new RangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeOption withCount(final Long count) {
        return new RangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }

    @Override
    public RangeOption withSelected(final Boolean selected) {
        return new RangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}
