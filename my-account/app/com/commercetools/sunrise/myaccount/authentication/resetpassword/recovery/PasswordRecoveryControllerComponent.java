package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.ctpevents.CustomerTokenCreatedHook;
import com.commercetools.sunrise.framework.hooks.ctprequests.CustomerCreatePasswordTokenCommandHook;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateContext;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContentFactory;
import io.commercetools.sunrise.email.EmailSender;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.mail.Message;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletionStage;

/**
 * This controller component is responsible for creating the password reset link from the
 * created {@link CustomerToken} and send it to the customers email address.
 */
public class PasswordRecoveryControllerComponent implements ControllerComponent, CustomerTokenCreatedHook, CustomerCreatePasswordTokenCommandHook {
    private final ResetPasswordReverseRouter resetPasswordReverseRouter;
    private final PasswordResetEmailPageContentFactory passwordResetEmailPageContentFactory;
    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;
    private final Locale locale;
    @Nullable
    private String customerEmail;

    @Inject
    protected PasswordRecoveryControllerComponent(final ResetPasswordReverseRouter resetPasswordReverseRouter,
                                                  final PasswordResetEmailPageContentFactory passwordResetEmailPageContentFactory,
                                                  final TemplateEngine templateEngine,
                                                  final EmailSender emailSender,
                                                  final Locale locale) {
        this.resetPasswordReverseRouter = resetPasswordReverseRouter;
        this.passwordResetEmailPageContentFactory = passwordResetEmailPageContentFactory;
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
        this.locale = locale;
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
        final PasswordResetEmailPageContent passwordResetEmailPageContent =
                passwordResetEmailPageContentFactory.create(passwordResetLink);
        final PageData pageData = new PageData();
        pageData.setContent(passwordResetEmailPageContent);

        final TemplateContext templateContext = new TemplateContext(pageData, Collections.singletonList(locale), null);
        final String emailText = templateEngine.render("password-reset-email", templateContext);

        return emailSender.send(msg -> {
            msg.setRecipients(Message.RecipientType.TO, getCustomerEmail());
            msg.setSubject(passwordResetEmailPageContent.getSubject(), "UTF-8");
            msg.setContent(emailText, "text/html");
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
