package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctpevents.ProductProjectionPagedResultLoadedHook;
import com.commercetools.sunrise.sessions.customer.CustomerInSession;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import play.libs.concurrent.HttpExecution;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

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
        return getShoppingList()
                .thenComposeAsync(this::getOrCreate, HttpExecution.defaultContext())
                .thenComposeAsync(this::storeInSession, HttpExecution.defaultContext());
    }

    @Override
    public CompletionStage<Wishlist> getWishList(final ShoppingList shoppingList, final long limit, final long offset) {
        final List<LineItem> lineItems = shoppingList.getLineItems() == null ?
                Collections.emptyList() :
                new ArrayList<>(shoppingList.getLineItems());
        Collections.reverse(lineItems);

        final long endIndex = offset + limit;
        final List<LineItem> pagedLineItems = offset < lineItems.size() ?
                lineItems.subList((int) offset, (int) Math.min(lineItems.size(), endIndex)) :
                Collections.emptyList();

        final List<String> productIds = pagedLineItems.stream()
                .map(LineItem::getProductId)
                .collect(Collectors.toList());

        final CompletionStage<PagedQueryResult<ProductProjection>> resultCompletionStage = productIds.isEmpty() ?
                CompletableFuture.completedFuture(null) :
                getSphereClient().execute(ProductProjectionQuery.ofCurrent()
                        .withPredicates(m -> m.id().isIn(productIds))
                        .withLimit(limit));

        final Map<String, Integer> productIdToLineItemIndex = pagedLineItems.stream()
                .collect(Collectors.toMap(LineItem::getProductId, pagedLineItems::indexOf));

        return resultCompletionStage
                .thenApply(pagedQueryResult ->  getOrderedBy(pagedQueryResult, productIdToLineItemIndex, offset, lineItems.size()))
                .thenApplyAsync(this::runProductProjectionPagedResultLoadedHook, HttpExecution.defaultContext())
                .thenComposeAsync((input) -> CompletableFuture.completedFuture(new Wishlist(shoppingList, input)), HttpExecution.defaultContext());
    }

    private PagedQueryResult<ProductProjection> getOrderedBy(final PagedQueryResult<ProductProjection> pagedQueryResult,
                                                             final Map<String, Integer> productIdToLineItemIndex,
                                                             final long offset,
                                                             final int total) {
        final List<ProductProjection> results = pagedQueryResult == null ?
                Collections.emptyList() : pagedQueryResult.getResults();
        final Long count = pagedQueryResult == null ? 0L : pagedQueryResult.getCount();
        Collections.sort(results, orderBy(productIdToLineItemIndex));

        return new PagedQueryResult<ProductProjection>() {
            @Override
            public Long getCount() {
                return count;
            }

            @Override
            public Long getOffset() {
                return offset;
            }

            @Override
            public List<ProductProjection> getResults() {
                return results;
            }

            @Override
            public Long getTotal() {
                return (long) total;
            }

            @Override
            public Long size() {
                return (long) results.size();
            }
        };
    }

    private Comparator<ProductProjection> orderBy(final Map<String, Integer> productIdToLineItemIndex) {
        return Comparator.comparing(productProjection -> productIdToLineItemIndex.get(productProjection.getId()));
    }

    private PagedQueryResult<ProductProjection> runProductProjectionPagedResultLoadedHook(final PagedQueryResult<ProductProjection> result) {
        ProductProjectionPagedResultLoadedHook.runHook(getHookRunner(), result);
        return result;
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

    private CompletionStage<Optional<ShoppingList>> getShoppingList() {
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
                .orElseGet(this::tryBuildQueryByWishlistId)
                .map(this::decorateQueryWithAdditionalInfo);
    }

    private Optional<ShoppingListQuery> tryBuildQueryByCustomerId() {
        return customerInSession.findCustomerId()
                .map(customerId -> ShoppingListQuery.of().plusPredicates(m -> m.customer().isInIds(Collections.singletonList(customerId))));
    }

    private Optional<ShoppingListQuery> tryBuildQueryByWishlistId() {
        return wishlistInSession.findWishlistId()
                .map(shoppingListId -> ShoppingListQuery.of().plusPredicates(m -> m.id().is(shoppingListId)));
    }

    private ShoppingListQuery decorateQueryWithAdditionalInfo(final ShoppingListQuery query) {
        return query
                .withSort(m -> m.createdAt().sort().desc())
                .withLimit(1);
    }
}
