package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class DefaultResetPasswordTokenControllerAction extends AbstractCustomerCreatePasswordTokenExecutor implements ResetPasswordTokenControllerAction {

    @Inject
    public DefaultResetPasswordTokenControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<CustomerToken> apply(final SendResetPasswordEmailFormData sendResetPasswordEmailFormData) {
        return executeRequest(buildRequest(sendResetPasswordEmailFormData));
    }

    protected CustomerCreatePasswordTokenCommand buildRequest(final SendResetPasswordEmailFormData formData) {
        return CustomerCreatePasswordTokenCommand.of(formData.email());
    }
}
