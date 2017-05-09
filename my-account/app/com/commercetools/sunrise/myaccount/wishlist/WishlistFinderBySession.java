package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.sessions.customer.CustomerInSession;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import play.libs.concurrent.HttpExecution;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class WishlistFinderBySession extends AbstractSphereRequestExecutor implements WishlistFinder {
    private final WishlistInSession wishlistInSession;
    private final CustomerInSession customerInSession;
    private final WishlistCreator wishlistCreator;

    @Inject
    protected WishlistFinderBySession(final SphereClient sphereClient, final HookRunner hookRunner,
                                      final WishlistInSession wishlistInSession, final CustomerInSession customerInSession,
                                      final WishlistCreator wishlistCreator) {
        super(sphereClient, hookRunner);
        this.wishlistInSession = wishlistInSession;
        this.customerInSession = customerInSession;
        this.wishlistCreator = wishlistCreator;
    }

    @Override
    public CompletionStage<ShoppingList> getOrCreate() {
        return get()
                .thenComposeAsync(this::getOrCreate, HttpExecution.defaultContext())
                .thenComposeAsync(this::storeInSession, HttpExecution.defaultContext());
    }

    private CompletionStage<ShoppingList> storeInSession(final ShoppingList input) {
        wishlistInSession.store(input);
        return CompletableFuture.completedFuture(input);
    }

    private CompletionStage<ShoppingList> getOrCreate(final Optional<ShoppingList> shoppingListOptional) {
        if (shoppingListOptional.isPresent()) {
            return CompletableFuture.completedFuture(shoppingListOptional.get());
        } else {
            return wishlistCreator.get();
        }
    }

    private CompletionStage<Optional<ShoppingList>> get() {
        return buildQuery()
                .map(this::executeRequest)
                .orElseGet(() -> completedFuture(Optional.empty()));
    }

    private CompletionStage<Optional<ShoppingList>> executeRequest(final ShoppingListQuery shoppingListQuery) {
        return getSphereClient().execute(shoppingListQuery)
                .thenApply(PagedQueryResult::head);
    }

    private Optional<ShoppingListQuery> buildQuery() {
        return tryBuildQueryByCustomerId()
                .map(Optional::of)
                .orElseGet(this::tryBuildQueryByCartId)
                .map(this::decorateQueryWithAdditionalInfo);
    }

    private Optional<ShoppingListQuery> tryBuildQueryByCustomerId() {
        return customerInSession.findCustomerId()
                .map(customerId -> ShoppingListQuery.of().plusPredicates(m -> m.customer().isInIds(Collections.singletonList(customerId))));
    }

    private Optional<ShoppingListQuery> tryBuildQueryByCartId() {
        return wishlistInSession.findWishlistId()
                .map(shoppingListId -> ShoppingListQuery.of().plusPredicates(m -> m.id().is(shoppingListId)));
    }

    private ShoppingListQuery decorateQueryWithAdditionalInfo(final ShoppingListQuery query) {
        return query
                .withExpansionPaths(m -> m.lineItems().variant())
                .withSort(cart -> cart.lastModifiedAt().sort().desc())
                .withLimit(1);
    }
}
