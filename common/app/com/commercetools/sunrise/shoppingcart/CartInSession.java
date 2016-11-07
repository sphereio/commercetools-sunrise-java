package com.commercetools.sunrise.shoppingcart;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.AbstractSerializedObjectInSession;
import com.google.inject.Injector;
import io.sphere.sdk.carts.Cart;
import play.Configuration;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CartInSession extends AbstractSerializedObjectInSession<Cart> {

    private static final String DEFAULT_CART_ID_SESSION_KEY = "sunrise-cart-id";
    private static final String DEFAULT_MINI_CART_SESSION_KEY = "sunrise-mini-cart";
    private final String cartIdSessionKey;
    private final String miniCartSessionKey;
    @Inject
    private Injector injector;

    @Inject
    public CartInSession(final Http.Context httpContext, final Configuration configuration) {
        super(httpContext.session());
        this.cartIdSessionKey = configuration.getString("session.cart.cartId", DEFAULT_CART_ID_SESSION_KEY);
        this.miniCartSessionKey = configuration.getString("session.cart.miniCart", DEFAULT_MINI_CART_SESSION_KEY);
    }

    public Optional<String> findCartId() {
        return findValueByKey(cartIdSessionKey);
    }

    public Optional<MiniCartBean> findMiniCart() {
        return findObjectByKey(miniCartSessionKey, MiniCartBean.class);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Cart cart) {
        final MiniCartBean miniCartBean = injector.getInstance(MiniCartBeanFactory.class).create(cart);
        overwriteObjectByKey(miniCartSessionKey, miniCartBean);
        overwriteValueByKey(cartIdSessionKey, cart.getId());
    }

    @Override
    protected void removeRelatedValuesFromSession() {
        removeValueByKey(miniCartSessionKey);
        removeValueByKey(cartIdSessionKey);
    }
}
