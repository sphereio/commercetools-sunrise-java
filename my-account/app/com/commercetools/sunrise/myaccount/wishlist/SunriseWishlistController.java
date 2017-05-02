package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentController;
import com.commercetools.sunrise.framework.controllers.WithQueryFlow;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.MyAccountController;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.utils.CompletableFutureUtils;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

public class SunriseWishlistController extends SunriseContentController implements MyAccountController, WithQueryFlow<ShoppingList> {
    private final MyWishlistCreator wishlistCreator;
    private final WishlistPageContentFactory wishlistPageContentFactory;

    @Inject
    protected SunriseWishlistController(final MyWishlistCreator wishlistCreator, final ContentRenderer contentRenderer, final WishlistPageContentFactory wishlistPageContentFactory) {
        super(contentRenderer);
        this.wishlistCreator = wishlistCreator;
        this.wishlistPageContentFactory = wishlistPageContentFactory;
    }

    public CompletionStage<Result> show(final String languageTag) {
        return wishlistCreator.get().thenComposeAsync(this::showPage, HttpExecution.defaultContext());
    }


    @SunriseRoute(MyWishlistReverseRouter.ADD_TO_WISHLIST)
    public CompletionStage<Result> addToWishlist(final String languageTag) {
        System.out.println("hhh");
        return CompletableFutureUtils.successful(null);
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
