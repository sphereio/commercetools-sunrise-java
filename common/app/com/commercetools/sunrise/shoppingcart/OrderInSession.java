package com.commercetools.sunrise.shoppingcart;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.AbstractInSession;
import io.sphere.sdk.orders.Order;
import play.Configuration;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class OrderInSession extends AbstractInSession<Order> {

    private static final String DEFAULT_LAST_ORDER_ID_SESSION_KEY = "sunrise-last-order-id";
    private final String lastOrderIdSessionKey;

    @Inject
    public OrderInSession(final Http.Context httpContext, final Configuration configuration) {
        super(httpContext.session());
        this.lastOrderIdSessionKey = configuration.getString("session.order.lastOrderId", DEFAULT_LAST_ORDER_ID_SESSION_KEY);
    }

    public Optional<String> findLastOrderId() {
        return findValueByKey(lastOrderIdSessionKey);
    }

    @Override
    protected void overwriteRelatedValuesInSession(final Order order) {
        overwriteValueByKey(lastOrderIdSessionKey, order.getId());
    }

    @Override
    protected void removeRelatedValuesFromSession() {
        removeValueByKey(lastOrderIdSessionKey);
    }
}
