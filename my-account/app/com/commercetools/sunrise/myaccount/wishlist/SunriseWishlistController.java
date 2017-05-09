package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to view the current wishlist.
 */
public class SunriseWishlistController extends SunriseContentController implements MyAccountController, WithQueryFlow<ShoppingList> {
    private final WishlistFinderBySession wishlistFinder;
    private final ClearWishlistControllerAction controllerAction;
    private final WishlistPageContentFactory wishlistPageContentFactory;

    @Inject
    protected SunriseWishlistController(final WishlistFinderBySession wishlistFinder, final ClearWishlistControllerAction controllerAction,
                                        final ContentRenderer contentRenderer, final WishlistPageContentFactory wishlistPageContentFactory) {
        super(contentRenderer);
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.wishlistPageContentFactory = wishlistPageContentFactory;
    }

    @SunriseRoute(MyWishlistReverseRouter.MY_WISHLIST_PAGE_CALL)
    public CompletionStage<Result> show(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(this::showPage, HttpExecution.defaultContext());
    }


    @SunriseRoute(MyWishlistReverseRouter.CLEAR_WISHLIST_PROCESS)
    public CompletionStage<Result> clear(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(controllerAction::apply)
                .thenComposeAsync(this::showPage, HttpExecution.defaultContext());
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
}
