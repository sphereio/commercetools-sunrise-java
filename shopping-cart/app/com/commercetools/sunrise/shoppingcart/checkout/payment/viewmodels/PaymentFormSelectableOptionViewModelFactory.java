package com.commercetools.sunrise.shoppingcart.checkout.payment.viewmodels;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.viewmodels.forms.SelectableViewModelFactory;
import io.sphere.sdk.payments.PaymentMethodInfo;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class PaymentFormSelectableOptionViewModelFactory extends SelectableViewModelFactory<PaymentFormSelectableOptionViewModel, PaymentMethodInfo, String> {

    private final MessagesResolver messagesResolver;

    @Inject
    public PaymentFormSelectableOptionViewModelFactory(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    protected final MessagesResolver getMessagesResolver() {
        return messagesResolver;
    }

    @Override
    protected PaymentFormSelectableOptionViewModel newViewModelInstance(final PaymentMethodInfo option, @Nullable final String selectedValue) {
        return new PaymentFormSelectableOptionViewModel();
    }

    @Override
    public final PaymentFormSelectableOptionViewModel create(final PaymentMethodInfo option, @Nullable final String selectedValue) {
        return super.create(option, selectedValue);
    }

    @Override
    protected final void initialize(final PaymentFormSelectableOptionViewModel viewModel, final PaymentMethodInfo option, @Nullable final String selectedValue) {
        fillLabel(viewModel, option, selectedValue);
        fillValue(viewModel, option, selectedValue);
        fillSelected(viewModel, option, selectedValue);
    }

    protected void fillLabel(final PaymentFormSelectableOptionViewModel viewModel, final PaymentMethodInfo option, @Nullable final String selectedValue) {
        final String label = Optional.ofNullable(option.getName())
                .flatMap(messagesResolver::get)
                .orElseGet(option::getMethod);
        viewModel.setLabel(label);
    }

    protected void fillValue(final PaymentFormSelectableOptionViewModel viewModel, final PaymentMethodInfo option, @Nullable final String selectedValue) {
        viewModel.setValue(option.getMethod());
    }

    protected void fillSelected(final PaymentFormSelectableOptionViewModel viewModel, final PaymentMethodInfo option, @Nullable final String selectedValue) {
        viewModel.setSelected(option.getMethod() != null && option.getMethod().equals(selectedValue));
    }
}
