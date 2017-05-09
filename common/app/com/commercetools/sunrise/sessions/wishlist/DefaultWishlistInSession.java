package com.commercetools.sunrise.sessions.wishlist;

import com.commercetools.sunrise.sessions.DataFromResourceStoringOperations;
import com.commercetools.sunrise.sessions.ObjectStoringSessionStrategy;
import io.sphere.sdk.shoppinglists.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Default implementation of {@link WishlistInSession}.
 */
public class DefaultWishlistInSession extends DataFromResourceStoringOperations<ShoppingList> implements  WishlistInSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistInSession.class);
    private static final String DEFAULT_WISHLIST_ID_SESSION_KEY = "sunrise-wishlist-id";

    private final String wishlistIdSessionKey;
    private final ObjectStoringSessionStrategy session;

    @Inject
    public DefaultWishlistInSession(final Configuration configuration, final ObjectStoringSessionStrategy session) {
        this.wishlistIdSessionKey = configuration.getString("session.wishlist.wishlistId", DEFAULT_WISHLIST_ID_SESSION_KEY);
        this.session = session;
    }

    @Override
    public Optional<String> findWishlistId() {
        return session.findValueByKey(wishlistIdSessionKey);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected void storeAssociatedData(@NotNull final ShoppingList value) {
        session.overwriteValueByKey(wishlistIdSessionKey, value.getId());
    }

    @Override
    protected void removeAssociatedData() {
        session.removeValueByKey(wishlistIdSessionKey);
    }
}
