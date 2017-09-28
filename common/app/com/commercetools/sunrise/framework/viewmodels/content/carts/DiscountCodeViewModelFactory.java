package com.commercetools.sunrise.framework.viewmodels.content.carts;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.google.inject.Inject;
import io.sphere.sdk.discountcodes.DiscountCode;

import java.util.Locale;

@RequestScoped
public class DiscountCodeViewModelFactory extends SimpleViewModelFactory<DiscountCodeViewModel, DiscountCode> {
    private final Locale locale;

    @Inject
    public DiscountCodeViewModelFactory(final Locale locale) {
        this.locale = locale;
    }

    @Override
    protected DiscountCodeViewModel newViewModelInstance(final DiscountCode input) {
        return new DiscountCodeViewModel();
    }

    @Override
    protected void initialize(final DiscountCodeViewModel viewModel, final DiscountCode input) {
        fillName(viewModel, input);
        fillDescription(viewModel, input);
    }

    protected void fillName(final DiscountCodeViewModel viewModel, final DiscountCode input) {
        final String name = input.getName() == null ? null : input.getName().get(locale) ;
        final String viewName = name == null ? input.getCode() : name;
        viewModel.setName(viewName);
    }

    protected void fillDescription(final DiscountCodeViewModel viewModel, final DiscountCode input) {
        final String description = input.getDescription() == null ? null : input.getDescription().get(locale);
        viewModel.setDescription(description);
    }
}
