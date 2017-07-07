package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.customers.CustomerToken;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultResetPasswordTokenControllerAction.class)
@FunctionalInterface
public interface ResetPasswordTokenControllerAction extends ControllerAction, Function<SendResetPasswordEmailFormData, CompletionStage<CustomerToken>> {

    @Override
    CompletionStage<CustomerToken> apply(SendResetPasswordEmailFormData sendResetPasswordEmailFormData);
}
