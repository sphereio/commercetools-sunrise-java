package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class DefaultPasswordRecoveryControllerAction extends AbstractCustomerCreatePasswordTokenExecutor implements PasswordRecoveryControllerAction {

    @Inject
    protected DefaultPasswordRecoveryControllerAction(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    @Override
    public CompletionStage<CustomerToken> apply(final PasswordRecoveryFormData recoveryEmailFormData) {
        return executeRequest(buildRequest(recoveryEmailFormData));
    }

    protected CustomerCreatePasswordTokenCommand buildRequest(final PasswordRecoveryFormData formData) {
        return CustomerCreatePasswordTokenCommand.of(formData.email());
    }
}
