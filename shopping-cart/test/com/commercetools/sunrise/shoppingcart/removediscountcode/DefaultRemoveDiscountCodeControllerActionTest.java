package com.commercetools.sunrise.shoppingcart.removediscountcode;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.RemoveDiscountCode;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.UpdateAction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultRemoveDiscountCodeControllerAction}.
 */
public class DefaultRemoveDiscountCodeControllerActionTest {
    @Mock
    private SphereClient sphereClient;
    @Mock
    private HookRunner hookRunner;
    @Mock
    private Cart cart;
    @Captor
    private ArgumentCaptor<CartUpdateCommand> cartUpdateCommandCaptor;

    @InjectMocks
    private DefaultRemoveDiscountCodeControllerAction defaultRemoveDiscountCodeControllerAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        final String discountCodeId = UUID.randomUUID().toString();

        final DefaultRemoveDiscountCodeFormData removeDiscountCodeFormData = new DefaultRemoveDiscountCodeFormData();
        removeDiscountCodeFormData.setDiscountCodeId(discountCodeId);

        when:
        {
            when(hookRunner.runUnaryOperatorHook(any(), any(), cartUpdateCommandCaptor.capture()))
                    .thenAnswer(invocation -> invocation.getArgument(2));
            when(hookRunner.runActionHook(any(), any(), any()))
                    .thenAnswer(invocation -> CompletableFuture.completedFuture(invocation.getArgument(2)));
            when(sphereClient.execute(any()))
                    .thenReturn(CompletableFuture.completedFuture(cart));
        }

        final Cart updatedCart = defaultRemoveDiscountCodeControllerAction.apply(cart, removeDiscountCodeFormData)
                .toCompletableFuture().get();

        then: {
            assertThat(updatedCart).isNotNull();

            final CartUpdateCommand cartUpdateCommand = cartUpdateCommandCaptor.getValue();
            assertThat(cartUpdateCommand.getUpdateActions()).hasSize(1);
            final UpdateAction<Cart> cartUpdateAction = cartUpdateCommand.getUpdateActions().get(0);
            assertThat(cartUpdateAction).isInstanceOf(RemoveDiscountCode.class);
            final RemoveDiscountCode removeDiscountCode = (RemoveDiscountCode) cartUpdateAction;
            assertThat(removeDiscountCode.getDiscountCode().getId()).isEqualTo(discountCodeId);
        }
    }
}
