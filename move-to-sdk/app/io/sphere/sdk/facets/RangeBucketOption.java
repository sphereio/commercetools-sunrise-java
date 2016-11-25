package io.sphere.sdk.facets;

public interface RangeBucketOption {

    String getLowerEndpoint();

    String getUpperEndpoint();

    Long getCount();

    Boolean getSelected();

    RangeBucketOption withLowerEndpoint(String lowerEndpoint);

    RangeBucketOption withUpperEndpoint(String upperEndpoint);

    RangeBucketOption withCount(Long count);

    RangeBucketOption withSelected(Boolean selected);

    static RangeBucketOption of(final String lowerEndpoint, final String upperEndpoint, final Long count, final Boolean selected){
        return new RangeBucketOptionImpl(lowerEndpoint, upperEndpoint, count, selected);
    }
}

