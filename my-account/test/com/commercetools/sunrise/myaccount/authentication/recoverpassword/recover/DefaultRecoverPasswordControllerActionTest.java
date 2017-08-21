package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctpevents.CustomerTokenCreatedHook;
import com.commercetools.sunrise.framework.hooks.ctprequests.CustomerCreatePasswordTokenCommandHook;
import com.commercetools.sunrise.framework.reverserouters.myaccount.resetpassword.ResetPasswordReverseRouter;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordEmailPageContent;
import com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover.viewmodels.RecoverPasswordEmailPageContentFactory;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import play.mvc.Call;
import play.mvc.Http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultRecoverPasswordControllerAction}.
 */
public class DefaultRecoverPasswordControllerActionTest {
    @Mock
    private SphereClient sphereClient;
    @Mock
    private HookRunner hookRunner;
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
    private DefaultRecoverPasswordControllerAction defaultPasswordRecoveryControllerAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Http.Context.current.set(context);
    }

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        final RecoverPasswordEmailPageContent recoverPasswordEmailPageContent = new RecoverPasswordEmailPageContent();
        final String emailContent = "email-content";
        final String tokenValue = "token-value";

        when:
        {
            when(sphereClient.execute(any()))
                    .thenReturn(CompletableFuture.completedFuture(customerToken));
            when(customerToken.getValue()).thenReturn(tokenValue);

            when(hookRunner.runUnaryOperatorHook(eq(CustomerCreatePasswordTokenCommandHook.class), any(), any()))
                    .thenAnswer(invocation -> invocation.getArgument(2));
            when(hookRunner.runEventHook(eq(CustomerTokenCreatedHook.class), any()))
                    .thenReturn(CompletableFuture.completedFuture(null));

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

        final CompletableFuture<CustomerToken> customerTokenCompletionStage =
                defaultPasswordRecoveryControllerAction.apply(formDataWithValidEmailOfExistingCustomer()).toCompletableFuture();

        then:
        {
            final CustomerToken customerToken = customerTokenCompletionStage.get();
            assertThat(customerTokenCompletionStage).isCompletedWithValueMatching(customerToken1 -> customerToken1.getValue().equals(tokenValue));
            assertThat(customerToken.getValue()).isEqualTo(tokenValue);
        }
    }

    private DefaultRecoverPasswordFormData formDataWithValidEmailOfExistingCustomer() {
        final DefaultRecoverPasswordFormData recoveryEmailFormData = new DefaultRecoverPasswordFormData();
        recoveryEmailFormData.setEmail("test@example.com");
        return recoveryEmailFormData;
    }
}
