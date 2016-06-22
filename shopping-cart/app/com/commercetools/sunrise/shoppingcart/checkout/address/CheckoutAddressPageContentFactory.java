package com.commercetools.sunrise.shoppingcart.checkout.address;

import com.commercetools.sunrise.common.models.SunriseDataBeanFactory;
import com.commercetools.sunrise.common.errors.ErrorsBean;
import com.commercetools.sunrise.common.template.i18n.I18nIdentifier;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Address;
import play.data.Form;

import javax.inject.Inject;

import static com.commercetools.sunrise.common.utils.FormUtils.extractAddress;
import static com.commercetools.sunrise.common.utils.FormUtils.extractBooleanFormField;

public class CheckoutAddressPageContentFactory extends SunriseDataBeanFactory {
    @Inject
    private CheckoutAddressFormBeanFactory formBeanFactory;

    public CheckoutAddressPageContent create(final Cart cart) {
        final CheckoutAddressPageContent pageContent = new CheckoutAddressPageContent();
        pageContent.setAddressForm(formBeanFactory.create(cart));
        setCommonData(pageContent);
        return pageContent;
    }

    public CheckoutAddressPageContent createWithAddressError(final Form<? extends CheckoutAddressFormDataLike> addressForm,
                                                                final ErrorsBean errors) {
        final CheckoutAddressPageContent pageContent = new CheckoutAddressPageContent();
        final Address shippingAddress = extractAddress(addressForm, "Shipping");
        final Address billingAddress = extractAddress(addressForm, "Billing");
        final boolean differentBillingAddress = extractBooleanFormField(addressForm, "billingAddressDifferentToBillingAddress");
        final CheckoutAddressFormBean formBean = new CheckoutAddressFormBean(shippingAddress, billingAddress, differentBillingAddress, userContext, projectContext, i18nResolver, configuration);
        formBean.setErrors(errors);
        pageContent.setAddressForm(formBean);
        setCommonData(pageContent);
        return pageContent;
    }

    private void setCommonData(final CheckoutAddressPageContent pageContent) {
        pageContent.setTitle(i18nResolver.getOrEmpty(userContext.locales(), I18nIdentifier.of("checkout:shippingPage.title")));
    }
}
