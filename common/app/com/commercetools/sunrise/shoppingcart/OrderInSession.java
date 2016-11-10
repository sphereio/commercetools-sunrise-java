package com.commercetools.sunrise.shoppingcart;

import com.commercetools.sunrise.common.contexts.RequestScoped;
import com.commercetools.sunrise.common.sessions.StorableDataFromResource;
import com.commercetools.sunrise.common.sessions.SessionStrategy;
import io.sphere.sdk.orders.Order;
import play.Configuration;

import javax.inject.Inject;
import java.util.Optional;

@RequestScoped
public class OrderInSession extends StorableDataFromResource<Order> {

    private static final String DEFAULT_LAST_ORDER_ID_SESSION_KEY = "sunrise-last-order-id";
    private final String lastOrderIdSessionKey;
    protected final SessionStrategy session;

    @Inject
    public OrderInSession(final SessionStrategy session, final Configuration configuration) {
        this.lastOrderIdSessionKey = configuration.getString("session.order.lastOrderId", DEFAULT_LAST_ORDER_ID_SESSION_KEY);
        this.session = session;
    }

    public Optional<String> findLastOrderId() {
        return session.findValueByKey(lastOrderIdSessionKey);
    }

    @Override
    protected void writeAssociatedData(final Order order) {
        session.overwriteValueByKey(lastOrderIdSessionKey, order.getId());
    }

    @Override
    protected void removeAssociatedData() {
        session.removeValueByKey(lastOrderIdSessionKey);
    }
}
