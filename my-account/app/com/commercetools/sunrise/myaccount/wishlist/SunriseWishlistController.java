package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistPaginationSettings;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistProductsPerPageFormSettings;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import com.commercetools.sunrise.search.pagination.EntriesPerPageFormSettings;
import com.commercetools.sunrise.search.pagination.PaginationSettings;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.libs.concurrent.HttpExecution;
import play.mvc.Http;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to view the current wishlist.
 */
public class SunriseWishlistController extends SunriseContentController implements MyAccountController, WithQueryFlow<Wishlist> {
    private final WishlistFinderBySession wishlistFinder;
    private final ClearWishlistControllerAction controllerAction;
    private final WishlistPageContentFactory wishlistPageContentFactory;

    private final PaginationSettings paginationSettings;
    private final EntriesPerPageFormSettings entriesPerPageFormSettings;

    @Inject
    protected SunriseWishlistController(final WishlistFinderBySession wishlistFinder,
                                        final ClearWishlistControllerAction controllerAction,
                                        final ContentRenderer contentRenderer,
                                        final WishlistPageContentFactory wishlistPageContentFactory,
                                        final WishlistPaginationSettings paginationSettings,
                                        final WishlistProductsPerPageFormSettings entriesPerPageFormSettings) {
        super(contentRenderer);
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.wishlistPageContentFactory = wishlistPageContentFactory;
        this.paginationSettings = paginationSettings;
        this.entriesPerPageFormSettings = entriesPerPageFormSettings;
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.WISHLIST_PAGE_CALL)
    public CompletionStage<Result> show(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(this::getWishlist, HttpExecution.defaultContext())
                .thenComposeAsync(this::showPage, HttpExecution.defaultContext());
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.CLEAR_WISHLIST_PROCESS)
    public CompletionStage<Result> clear(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(controllerAction::apply, HttpExecution.defaultContext())
                .thenComposeAsync(this::getWishlist, HttpExecution.defaultContext())
                .thenComposeAsync(this::showPage, HttpExecution.defaultContext());
    }

    private CompletionStage<Wishlist> getWishlist(final ShoppingList shoppingList) {
        final Http.Context context = Http.Context.current();
        final long limit = entriesPerPageFormSettings.getLimit(context);
        final long offset = paginationSettings.getOffset(context, limit);

        return wishlistFinder.getWishList(shoppingList, limit, offset);
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "my-account-wishlist";
    }

    @Override
    public PageContent createPageContent(final Wishlist input) {
        return wishlistPageContentFactory.create(input);
    }
}
