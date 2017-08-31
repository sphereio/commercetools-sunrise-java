package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.commercetools.sunrise.email.EmailSender;
import io.commercetools.sunrise.email.MessageEditor;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultRecoverPasswordControllerAction}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultRecoverPasswordControllerActionTest {

    private static final String CUSTOMER_EMAIL = "someone@mail.com";

    @Mock
    private SphereClient sphereClient;
    @Mock
    private HookRunner dummyHookRunner;
    @Mock
    private EmailSender emailSender;
    @Mock
    private RecoverPasswordMessageEditorProvider dummyMessageEditorProvider;

    @InjectMocks
    private DefaultRecoverPasswordControllerAction defaultPasswordRecoveryControllerAction;

    @Mock
    private RecoverPasswordFormData formDataWithValidEmail;
    @Mock
    private CustomerToken dummyForgetPasswordToken;
    private MessageEditor fakeMessageEditor = msg -> {};

    @Before
    public void setup() {
        when(formDataWithValidEmail.email()).thenReturn(CUSTOMER_EMAIL);
        when(dummyMessageEditorProvider.get(notNull(), notNull())).thenReturn(completedFuture(fakeMessageEditor));
    }

    @Test
    public void sendsEmailOnValidCustomerToken() throws Exception {
        mockSphereClientThatReturnsCustomerToken();
        mockEmailSenderWithSuccessfulOutcome();

        final CustomerToken resetPasswordToken = executeDefaultControllerAction();

        verify(sphereClient).execute(CustomerCreatePasswordTokenCommand.of(CUSTOMER_EMAIL));
        verify(emailSender).send(fakeMessageEditor);
        verify(dummyMessageEditorProvider).get(dummyForgetPasswordToken, formDataWithValidEmail);

        assertThat(resetPasswordToken).isEqualTo(dummyForgetPasswordToken);
    }

    private CustomerToken executeDefaultControllerAction() throws Exception {
        return defaultPasswordRecoveryControllerAction.apply(formDataWithValidEmail).toCompletableFuture().get();
    }

    private void mockEmailSenderWithSuccessfulOutcome() {
        when(emailSender.send(notNull())).thenReturn(completedFuture("email-id"));
    }

    private void mockSphereClientThatReturnsCustomerToken() {
        when(sphereClient.execute(notNull()))
                .thenReturn(completedFuture(dummyForgetPasswordToken));
    }
}
