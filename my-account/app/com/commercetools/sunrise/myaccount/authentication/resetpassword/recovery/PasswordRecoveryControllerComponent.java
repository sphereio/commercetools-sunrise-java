package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.ctpevents.CustomerTokenCreatedHook;
import com.commercetools.sunrise.framework.hooks.ctprequests.CustomerCreatePasswordTokenCommandHook;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import io.commercetools.sunrise.email.EmailSender;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import play.Logger;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.mail.Message;
import java.util.concurrent.CompletionStage;

/**
 * This controller component is responsible for creating the password reset link from the
 * created {@link CustomerToken} and send it to the customers email address.
 */
public class PasswordRecoveryControllerComponent implements ControllerComponent, CustomerTokenCreatedHook, CustomerCreatePasswordTokenCommandHook {
    private final ResetPasswordReverseRouter resetPasswordReverseRouter;
    private final EmailSender emailSender;
    @Nullable
    private String customerEmail;

    @Inject
    protected PasswordRecoveryControllerComponent(final ResetPasswordReverseRouter resetPasswordReverseRouter, final EmailSender emailSender) {
        this.resetPasswordReverseRouter = resetPasswordReverseRouter;
        this.emailSender = emailSender;
    }

    /**
     * Gets the customers email from the {@link CustomerCreatePasswordTokenCommand}.
     *
     * @param customerCreatePasswordTokenCommand the command from which to retrieve the customers email
     * @return the unmodified command
     */
    @Override
    public CustomerCreatePasswordTokenCommand onCustomerCreatePasswordTokenCommand(final CustomerCreatePasswordTokenCommand customerCreatePasswordTokenCommand) {
        customerEmail = customerCreatePasswordTokenCommand.getEmail();
        return customerCreatePasswordTokenCommand;
    }

    /**
     * Creates a password recovery link from the given reset password token and sends an email with the
     * password recovery link to {@link #getCustomerEmail()}.
     *
     * @param resetPasswordToken the reset password token
     * @return the completion stage which creates the recovery email
     */
    @Override
    public CompletionStage<?> onCustomerTokenCreated(final CustomerToken resetPasswordToken) {
        final String passwordResetLink = createPasswordResetLink(resetPasswordToken);
        Logger.debug("Password reset link {}", passwordResetLink);
        return emailSender.send(msg -> {
            msg.setFrom("test@example.com");
            msg.setRecipients(Message.RecipientType.TO, getCustomerEmail());
            msg.setSubject("The new mail service", "UTF-8");
            msg.setText("test me " + passwordResetLink);
        });
    }

    protected String createPasswordResetLink(final CustomerToken resetPasswordToken) {
        final Http.Request request = Http.Context.current().request();
        final String absoluteURL = resetPasswordReverseRouter.resetPasswordPageCall(resetPasswordToken.getValue())
                .absoluteURL(request);

        return absoluteURL;
    }

    @Nullable
    protected String getCustomerEmail() {
        return customerEmail;
    }
}
