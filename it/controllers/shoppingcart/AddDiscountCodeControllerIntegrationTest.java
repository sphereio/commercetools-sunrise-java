package controllers.shoppingcart;

import com.commercetools.sunrise.it.WithSphereClient;
import com.commercetools.sunrise.sessions.cart.CartInSession;
import com.google.inject.AbstractModule;
import io.sphere.sdk.client.SphereClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.commercetools.sunrise.it.CartTestFixtures.withTaxedAndFilledCart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class AddDiscountCodeControllerIntegrationTest extends WithSphereClient {
    @Mock
    private CartInSession cartInSession;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(SphereClient.class).toInstance(sphereClient);
                        bind(CartInSession.class).toInstance(cartInSession);
                    }
                }).build();
    }

    @Test
    public void showsErrorOnNonExistentDiscountCode() throws Exception {
        withTaxedAndFilledCart(sphereClient, cart -> {
            when(cartInSession.findCartId()).thenReturn(Optional.of(cart.getId()));

            final Map<String, String> bodyForm = new HashMap<>();
            bodyForm.put("code", "NON");
            final Result result = route(new Http.RequestBuilder()
                    .uri("/en/cart/discount/add")
                    .method(POST)
                    .bodyForm(bodyForm));

            assertThat(result.status()).isEqualTo(BAD_REQUEST);
            assertThat(contentAsString(result)).contains("Invalid discount code");

            return cart;
        });
    }
}
