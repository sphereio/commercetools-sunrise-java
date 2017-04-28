package com.commercetools.sunrise.myaccount.wishlist.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

import java.util.List;

/**
 * This class represents a generic list view model.
 */
public class GenericListViewModel<T> extends ViewModel {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(final List<T> list) {
        this.list = list;
    }
}
