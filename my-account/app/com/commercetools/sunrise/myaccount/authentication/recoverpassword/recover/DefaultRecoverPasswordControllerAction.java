package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class DefaultRecoverPasswordControllerAction extends AbstractCustomerCreatePasswordTokenExecutor implements RecoverPasswordControllerAction {

    @Inject
    protected DefaultRecoverPasswordControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<CustomerToken> apply(final RecoverPasswordFormData recoveryEmailFormData) {
        return executeRequest(buildRequest(recoveryEmailFormData));
    }

    protected CustomerCreatePasswordTokenCommand buildRequest(final RecoverPasswordFormData formData) {
        return CustomerCreatePasswordTokenCommand.of(formData.email());
    }
}
