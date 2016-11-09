package com.commercetools.sunrise.shoppingcart;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.AssociatedDataResourceStateWriter;
import com.commercetools.sunrise.common.sessions.ObjectHttpSessionStrategy;
import com.google.inject.Injector;
import io.sphere.sdk.carts.Cart;
import play.Configuration;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CartInSession extends AssociatedDataResourceStateWriter<Cart> {

    private static final String DEFAULT_CART_ID_SESSION_KEY = "sunrise-cart-id";
    private static final String DEFAULT_MINI_CART_SESSION_KEY = "sunrise-mini-cart";
    private final String cartIdSessionKey;
    private final String miniCartSessionKey;
    private final ObjectHttpSessionStrategy session;
    @Inject
    private Injector injector;

    @Inject
    public CartInSession(final ObjectHttpSessionStrategy session, final Configuration configuration) {
        this.cartIdSessionKey = configuration.getString("session.cart.cartId", DEFAULT_CART_ID_SESSION_KEY);
        this.miniCartSessionKey = configuration.getString("session.cart.miniCart", DEFAULT_MINI_CART_SESSION_KEY);
        this.session = session;
    }

    public Optional<String> findCartId() {
        return session.findValueByKey(cartIdSessionKey);
    }

    public Optional<MiniCartBean> findMiniCart() {
        return session.findObjectByKey(miniCartSessionKey, MiniCartBean.class);
    }

    @Override
    protected void overwriteAssociatedDataInSession(final Cart cart) {
        final MiniCartBean miniCartBean = injector.getInstance(MiniCartBeanFactory.class).create(cart);
        session.overwriteObjectByKey(miniCartSessionKey, miniCartBean);
        session.overwriteValueByKey(cartIdSessionKey, cart.getId());
    }

    @Override
    protected void removeAssociatedDataFromSession() {
        session.removeValueByKey(miniCartSessionKey);
        session.removeValueByKey(cartIdSessionKey);
    }
}
