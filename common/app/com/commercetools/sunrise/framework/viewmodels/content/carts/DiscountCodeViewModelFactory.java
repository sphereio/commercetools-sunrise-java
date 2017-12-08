package com.commercetools.sunrise.framework.viewmodels.content.carts;

import com.commercetools.sunrise.framework.i18n.MessagesResolver;
import com.commercetools.sunrise.framework.viewmodels.SimpleViewModelFactory;
import com.google.inject.Inject;
import io.sphere.sdk.discountcodes.DiscountCode;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class DiscountCodeViewModelFactory extends SimpleViewModelFactory<DiscountCodeViewModel, DiscountCode> {

    private final MessagesResolver messagesResolver;

    @Inject
    DiscountCodeViewModelFactory(final MessagesResolver messagesResolver) {
        this.messagesResolver = messagesResolver;
    }

    protected final MessagesResolver getMessagesResolver() {
        return messagesResolver;
    }

    @Override
    protected DiscountCodeViewModel newViewModelInstance(final DiscountCode discountCode) {
        return new DiscountCodeViewModel();
    }

    @Override
    protected void initialize(final DiscountCodeViewModel viewModel, final DiscountCode discountCode) {
        fillId(viewModel, discountCode);
        fillName(viewModel, discountCode);
        fillDescription(viewModel, discountCode);
    }

    protected void fillId(final DiscountCodeViewModel viewModel, final DiscountCode discountCode) {
        viewModel.setId(discountCode.getId());
    }

    protected void fillName(final DiscountCodeViewModel viewModel, final DiscountCode discountCode) {
        final String displayName = Optional.ofNullable(discountCode.getName())
                .flatMap(messagesResolver::get)
                .orElseGet(discountCode::getCode);
        viewModel.setName(displayName);
    }

    protected void fillDescription(final DiscountCodeViewModel viewModel, final DiscountCode discountCode) {
        viewModel.setDescription(discountCode.getDescription());
    }
}
