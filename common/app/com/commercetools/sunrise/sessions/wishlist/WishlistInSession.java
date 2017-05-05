package com.commercetools.sunrise.sessions.wishlist;

import com.commercetools.sunrise.sessions.ResourceStoringOperations;
import com.google.inject.ImplementedBy;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;
import java.util.Optional;

@ImplementedBy(DefaultWishlistInSession.class)
public interface WishlistInSession extends ResourceStoringOperations<ShoppingList> {

    Optional<String> findWishlistId();

    @Override
    void store(@Nullable final ShoppingList wishlist);

    @Override
    void remove();
}
