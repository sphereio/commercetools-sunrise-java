package com.commercetools.sunrise.shoppingcart;

import com.commercetools.sunrise.common.sessions.AbstractCachedSessionHandler;
import com.google.inject.Injector;
import io.sphere.sdk.carts.Cart;
import play.Configuration;
import play.cache.CacheApi;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CartSessionHandler extends AbstractCachedSessionHandler<Cart> {

    private static final String DEFAULT_CART_ID_SESSION_KEY = "sunrise-cart-id";
    private static final String DEFAULT_MINI_CART_SESSION_KEY = "sunrise-mini-cart";
    private final String cartIdSessionKey;
    private final String miniCartSessionKey;
    @Inject
    private Injector injector;
    @Inject
    private CacheApi cacheApi;

    @Inject
    public CartSessionHandler(final Configuration configuration) {
        this.cartIdSessionKey = configuration.getString("session.cart.cartId", DEFAULT_CART_ID_SESSION_KEY);
        this.miniCartSessionKey = configuration.getString("session.cart.miniCart", DEFAULT_MINI_CART_SESSION_KEY);
    }

    public Optional<String> findCartId(final Http.Session session) {
        return findValueByKey(session, cartIdSessionKey);
    }

    public Optional<MiniCartBean> findMiniCart(final Http.Session session) {
        return findCachedValueByKey(session, cacheApi, miniCartSessionKey);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Http.Session session, final Cart cart) {
        final MiniCartBean miniCartBean = injector.getInstance(MiniCartBeanFactory.class).create(cart);
        final String miniCartCacheKey = cart.getId() + miniCartSessionKey;
        overwriteCachedValueByKey(session, cacheApi, miniCartCacheKey, miniCartSessionKey, miniCartBean);
        overwriteValueByKey(session, cartIdSessionKey, cart.getId());
    }

    @Override
    protected void removeRelatedValuesFromSession(final Http.Session session) {
        removeCachedValueByKey(session, cacheApi, miniCartSessionKey);
        removeValueByKey(session, cartIdSessionKey);
    }
}
