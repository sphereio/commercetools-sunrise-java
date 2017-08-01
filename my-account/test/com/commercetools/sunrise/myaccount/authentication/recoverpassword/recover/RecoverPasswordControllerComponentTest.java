package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordEmailPageContent;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordEmailPageContentFactory;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import play.mvc.Call;
import play.mvc.Http;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

/**
 * Unit test for {@link RecoverPasswordControllerComponent}.
 */
public class RecoverPasswordControllerComponentTest {
    @Mock
    private ResetPasswordReverseRouter resetPasswordReverseRouter;
    @Mock
    private RecoverPasswordEmailPageContentFactory recoverPasswordEmailPageContentFactory;
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

    @Captor
    private ArgumentCaptor<MessageEditor> messageEditorCaptor;

    @InjectMocks
    private RecoverPasswordControllerComponent recoverPasswordControllerComponent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Http.Context.current.set(context);
    }

    @Test
    public void sendRecoveryEmail() throws Exception {
        final String customerEmail = "test@example.com";
        final CustomerCreatePasswordTokenCommand customerCreatePasswordTokenCommand =
                CustomerCreatePasswordTokenCommand.of(customerEmail);

        final RecoverPasswordEmailPageContent recoverPasswordEmailPageContent = new RecoverPasswordEmailPageContent();
        final String emailContent = "email-content";

        when:
        {
            final String tokenValue = "test-token";

            when(customerToken.getValue()).thenReturn(tokenValue);
            when(context.request()).thenReturn(request);
            final String passwordResetLink = "http://my.password";
            when(call.absoluteURL(request)).thenReturn(passwordResetLink);
            when(resetPasswordReverseRouter.resetPasswordPageCall(tokenValue)).thenReturn(call);

            recoverPasswordEmailPageContent.setPasswordResetUrl(passwordResetLink);
            final String emailSubject = "email-subject";
            recoverPasswordEmailPageContent.setSubject(emailSubject);

            when(recoverPasswordEmailPageContentFactory.create(passwordResetLink)).thenReturn(recoverPasswordEmailPageContent);
            when(templateEngine.render(eq("password-reset-email"), notNull())).thenReturn(emailContent);
            when(emailSender.send(messageEditorCaptor.capture())).thenReturn(CompletableFuture.completedFuture(null));
        }

        recoverPasswordControllerComponent.onCustomerCreatePasswordTokenCommand(customerCreatePasswordTokenCommand);
        recoverPasswordControllerComponent.onCustomerTokenCreated(customerToken);

        then:
        {
            final MessageEditor messageEditor = messageEditorCaptor.getValue();

            final MimeMessage mimeMessage = mock(MimeMessage.class);
            messageEditor.edit(mimeMessage);

            verify(mimeMessage).setRecipients(Message.RecipientType.TO, customerEmail);
            verify(mimeMessage).setSubject(recoverPasswordEmailPageContent.getSubject(), "UTF-8");
            verify(mimeMessage).setContent(emailContent, "text/html");
        }
    }
}
