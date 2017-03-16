package com.commercetools.sunrise.search.facetedsearch.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

import java.util.List;

public class FacetViewModel extends ViewModel {

    private String key;
    private String label;
    private boolean isAvailable;
    private boolean isCountHidden;
    private boolean multiSelect;
    private boolean matchingAll;
    private List<FacetOptionViewModel> limitedOptions;
    private SliderRangeEndpointViewModel endpoints;

    public FacetViewModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(final boolean available) {
        isAvailable = available;
    }

    public boolean isCountHidden() {
        return isCountHidden;
    }

    public void setCountHidden(final boolean countHidden) {
        isCountHidden = countHidden;
    }

    public List<FacetOptionViewModel> getLimitedOptions() {
        return limitedOptions;
    }

    public void setLimitedOptions(final List<FacetOptionViewModel> limitedOptions) {
        this.limitedOptions = limitedOptions;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(final boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public boolean isMatchingAll() {
        return matchingAll;
    }

    public void setMatchingAll(final boolean matchingAll) {
        this.matchingAll = matchingAll;
    }

    public SliderRangeEndpointViewModel getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(final SliderRangeEndpointViewModel endpoints) {
        this.endpoints = endpoints;
    }
}
