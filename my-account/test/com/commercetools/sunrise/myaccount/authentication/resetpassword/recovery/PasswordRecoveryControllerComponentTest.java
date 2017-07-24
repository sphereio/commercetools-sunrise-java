package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContent;
import com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery.viewmodels.PasswordResetEmailPageContentFactory;
import io.commercetools.sunrise.email.EmailSender;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.mvc.Call;
import play.mvc.Http;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link PasswordRecoveryControllerComponent}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PasswordRecoveryControllerComponentTest {
    @Mock
    private ResetPasswordReverseRouter resetPasswordReverseRouter;
    @Mock
    private PasswordResetEmailPageContentFactory passwordResetEmailPageContentFactory;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private EmailSender emailSender;
    @Mock
    private CustomerToken customerToken;
    @Mock
    private Http.Context context;
    @Mock
    private Http.Request request;
    @Mock
    private Call call;

    private PasswordRecoveryControllerComponent passwordRecoveryControllerComponent;

    @Before
    public void setup() {
        passwordRecoveryControllerComponent = new PasswordRecoveryControllerComponent(resetPasswordReverseRouter,
                passwordResetEmailPageContentFactory,
                templateEngine,
                emailSender,
                Locale.ENGLISH);

        Http.Context.current.set(context);
    }

    @Test
    public void onCustomerCreatePasswordTokenCommand() {
        final String customerEmail = "test@example.com";
        final CustomerCreatePasswordTokenCommand customerCreatePasswordTokenCommand =
                CustomerCreatePasswordTokenCommand.of(customerEmail);

        assertThat(passwordRecoveryControllerComponent.getCustomerEmail()).isNull();
        passwordRecoveryControllerComponent.onCustomerCreatePasswordTokenCommand(customerCreatePasswordTokenCommand);
        assertThat(passwordRecoveryControllerComponent.getCustomerEmail()).isEqualTo(customerEmail);
    }

    @Test
    public void onCustomerTokenCreated() {
        final String tokenValue = "test-token";

        when(customerToken.getValue()).thenReturn(tokenValue);
        when(context.request()).thenReturn(request);
        final String passwordResetLink = "http://my.password";
        when(call.absoluteURL(request)).thenReturn(passwordResetLink);
        when(resetPasswordReverseRouter.resetPasswordPageCall(tokenValue)).thenReturn(call);

        final PasswordResetEmailPageContent passwordResetEmailPageContent = new PasswordResetEmailPageContent();
        passwordResetEmailPageContent.setPasswordResetUrl(passwordResetLink);

        when(passwordResetEmailPageContentFactory.create(passwordResetLink)).thenReturn(passwordResetEmailPageContent);
        when(templateEngine.render(eq("password-reset-email"), notNull())).thenReturn("email-content");

        passwordRecoveryControllerComponent.onCustomerTokenCreated(customerToken);

        verify(emailSender).send(notNull());
    }
}
