package io.sphere.sdk.facets;

public interface BucketRangeOption {

    String getLowerEndpoint();

    String getUpperEndpoint();

    Long getCount();

    Boolean isSelected();

    BucketRangeOption withLowerEndpoint(String lowerEndpoint);

    BucketRangeOption withUpperEndpoint(String upperEndpoint);

    BucketRangeOption withCount(Long count);

    BucketRangeOption withSelected(Boolean selected);

    static BucketRangeOption of(final String lowerEndpoint, final String upperEndpoint, final Long count, final Boolean selected){
        return new BucketRangeOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}

