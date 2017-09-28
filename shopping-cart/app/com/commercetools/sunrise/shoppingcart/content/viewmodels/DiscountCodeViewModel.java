package com.commercetools.sunrise.shoppingcart.content.viewmodels;

import com.commercetools.sunrise.framework.viewmodels.ViewModel;

public class DiscountCodeViewModel extends ViewModel {
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
