package com.commercetools.sunrise.myaccount.authentication.resetpassword.sendemail;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctprequests.CustomerCreatePasswordTokenCommandHook;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;

import java.util.concurrent.CompletionStage;

/**
 * An abstract executor to create a customer password reset token.
 *
 * @see CustomerCreatePasswordTokenCommand
 */
public class AbstractCustomerCreatePasswordTokenExecutor extends AbstractSphereRequestExecutor {

    protected AbstractCustomerCreatePasswordTokenExecutor(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    protected final CompletionStage<CustomerToken> executeRequest(final CustomerCreatePasswordTokenCommand baseCommand) {
        final CustomerCreatePasswordTokenCommand command = CustomerCreatePasswordTokenCommandHook.runHook(getHookRunner(), baseCommand);
        return getSphereClient().execute(command);
    }
}
