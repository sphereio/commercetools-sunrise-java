package com.commercetools.sunrise.search.facetedsearch.viewmodels;

public class SliderRangeFacetViewModel extends FacetViewModel {

    private SliderRangeEndpointViewModel endpoints;

    public SliderRangeFacetViewModel() {
    }

    public SliderRangeEndpointViewModel getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(final SliderRangeEndpointViewModel endpoints) {
        this.endpoints = endpoints;
    }
}
