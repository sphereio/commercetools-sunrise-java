package com.commercetools.sunrise.myaccount.authentication.resetpassword.recovery;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctpevents.CustomerTokenCreatedHook;
import com.commercetools.sunrise.framework.hooks.ctprequests.CustomerCreatePasswordTokenCommandHook;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultPasswordRecoveryControllerAction}.
 */
public class DefaultPasswordRecoveryControllerActionTest {
    @Mock
    private SphereClient sphereClient;
    @Mock
    private HookRunner hookRunner;
    @Mock
    private CustomerToken customerToken;
    @Captor
    private ArgumentCaptor<CustomerCreatePasswordTokenCommand> customerCreatePasswordTokenCommandCaptor;

    @InjectMocks
    private DefaultPasswordRecoveryControllerAction defaultPasswordRecoveryControllerAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        final DefaultPasswordRecoveryFormData recoveryEmailFormData = new DefaultPasswordRecoveryFormData();
        recoveryEmailFormData.setEmail("test@example.com");
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
        }

        final CompletableFuture<CustomerToken> customerTokenCompletionStage =
                (CompletableFuture<CustomerToken>) defaultPasswordRecoveryControllerAction.apply(recoveryEmailFormData);

        then:
        {
            final CustomerToken customerToken = customerTokenCompletionStage.get();
            assertThat(customerToken.getValue()).isEqualTo(tokenValue);
        }
    }
}
