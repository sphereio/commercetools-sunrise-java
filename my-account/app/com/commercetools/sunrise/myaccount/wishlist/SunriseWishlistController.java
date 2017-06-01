package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to view the current wishlist.
 */
public class SunriseWishlistController extends SunriseContentController implements WithQueryFlow<ShoppingList>, WithRequiredWishlist {
    private final WishlistFinderBySession wishlistFinder;
    private final ClearWishlistControllerAction controllerAction;
    private final WishlistPageContentFactory wishlistPageContentFactory;

    @Inject
    protected SunriseWishlistController(final WishlistFinderBySession wishlistFinder,
                                        final ClearWishlistControllerAction controllerAction,
                                        final ContentRenderer contentRenderer,
                                        final WishlistPageContentFactory wishlistPageContentFactory) {
        super(contentRenderer);
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.wishlistPageContentFactory = wishlistPageContentFactory;
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.WISHLIST_PAGE)
    public CompletionStage<Result> show(final String languageTag) {
        return requireWishlist(this::showPage);
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "my-account-wishlist";
    }

    @Override
    public PageContent createPageContent(final ShoppingList input) {
        return wishlistPageContentFactory.create(input);
    }

    @Override
    public WishlistFinder getWishlistFinder() {
        return wishlistFinder;
    }

    @Override
    public CompletionStage<Result> handleNotFoundWishlist() {
        return okResultWithPageContent(wishlistPageContentFactory.create(null));
    }
}
