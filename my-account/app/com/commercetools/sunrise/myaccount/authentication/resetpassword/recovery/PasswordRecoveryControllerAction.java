package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.controllers.ControllerAction;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.customers.CustomerToken;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ImplementedBy(DefaultPasswordRecoveryControllerAction.class)
@FunctionalInterface
public interface PasswordRecoveryControllerAction extends ControllerAction, Function<PasswordRecoveryFormData, CompletionStage<CustomerToken>> {

    @Override
    CompletionStage<CustomerToken> apply(PasswordRecoveryFormData recoveryEmailFormData);
}
