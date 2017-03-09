package io.sphere.sdk.facets;

public interface RangeOption {

    String getLowerEndpoint();

    String getUpperEndpoint();

    Long getCount();

    Boolean isSelected();

    RangeOption withLowerEndpoint(String lowerEndpoint);

    RangeOption withUpperEndpoint(String upperEndpoint);

    RangeOption withCount(Long count);

    RangeOption withSelected(Boolean selected);

    static RangeOption of(final String lowerEndpoint, final String upperEndpoint, final Long count, final Boolean selected){
        return new RangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}
