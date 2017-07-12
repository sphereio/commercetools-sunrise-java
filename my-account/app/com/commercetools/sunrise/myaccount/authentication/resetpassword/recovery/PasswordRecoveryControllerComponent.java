package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.ctpevents.CustomerTokenCreatedHook;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import io.sphere.sdk.customers.CustomerToken;
import play.Logger;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * This controller component is responsible for creating the password reset link from the
 * created {@link CustomerToken} and send it to the customers email address.
 */
public class PasswordRecoveryControllerComponent implements ControllerComponent, CustomerTokenCreatedHook {
    private final ResetPasswordReverseRouter resetPasswordReverseRouter;

    @Inject
    protected PasswordRecoveryControllerComponent(final ResetPasswordReverseRouter resetPasswordReverseRouter) {
        this.resetPasswordReverseRouter = resetPasswordReverseRouter;
    }

    @Override
    public CompletionStage<?> onCustomerTokenCreated(final CustomerToken resetPasswordToken) {
        final String passwordResetLink = createPasswordResetLink(resetPasswordToken);
        Logger.debug("Password reset link {}", passwordResetLink);
        return completedFuture(null);
    }

    protected String createPasswordResetLink(final CustomerToken resetPasswordToken) {
        final Http.Request request = Http.Context.current().request();
        final String absoluteURL = resetPasswordReverseRouter.resetPasswordPageCall(resetPasswordToken.getValue())
                .absoluteURL(request);

        return absoluteURL;
    }
}
