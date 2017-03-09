package com.commercetools.sunrise.search.facetedsearch.old;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

import java.util.List;

public class FacetSelectorListViewModel extends ViewModel {

    private List<FacetSelectorViewModel> list;

    public FacetSelectorListViewModel() {
    }

    public List<FacetSelectorViewModel> getList() {
        return list;
    }

    public void setList(final List<FacetSelectorViewModel> list) {
        this.list = list;
    }
}
