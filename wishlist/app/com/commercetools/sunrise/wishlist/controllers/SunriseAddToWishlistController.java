package com.commercetools.sunrise.wishlist.controllers;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.wishlist.WishlistCreator;
import com.commercetools.sunrise.wishlist.WishlistFinder;
import com.commercetools.sunrise.wishlist.WithRequiredWishlist;
import com.commercetools.sunrise.wishlist.viewmodels.AddWishlistLineItemFormData;
import com.commercetools.sunrise.wishlist.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public abstract class SunriseAddToWishlistController extends SunriseContentFormController
        implements WithContentFormFlow<ShoppingList, ShoppingList, AddWishlistLineItemFormData>, WithRequiredWishlist {
    private final AddWishlistLineItemFormData formData;
    private final WishlistPageContentFactory wishlistPageContentFactory;
    private final WishlistCreator wishlistCreator;
    private final WishlistFinder wishlistFinder;
    private final AddToWishlistControllerAction controllerAction;

    @Inject
    protected SunriseAddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                             final WishlistPageContentFactory wishlistPageContentFactory,
                                             final AddWishlistLineItemFormData formData,
                                             final WishlistCreator wishlistCreator,
                                             final WishlistFinder wishlistFinder,
                                             final AddToWishlistControllerAction controllerAction) {
        super(contentRenderer, formFactory);
        this.wishlistPageContentFactory = wishlistPageContentFactory;
        this.formData = formData;
        this.wishlistCreator = wishlistCreator;
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
    }

    @Override
    public Class<? extends AddWishlistLineItemFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.ADD_TO_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return requireWishlist(this::processForm);
    }

    @Override
    public CompletionStage<ShoppingList> executeAction(final ShoppingList input, final AddWishlistLineItemFormData formData) {
        return controllerAction.apply(input, formData);
    }

    public abstract CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final AddWishlistLineItemFormData formData);

    @Override
    public WishlistFinder getWishlistFinder() {
        return wishlistFinder;
    }

    @Override
    public PageContent createPageContent(final ShoppingList input, final Form<? extends AddWishlistLineItemFormData> form) {
        return wishlistPageContentFactory.create(input);
    }

    @Override
    public CompletionStage<Result> handleNotFoundWishlist() {
        return wishlistCreator.get()
                .thenComposeAsync(this::processForm, HttpExecution.defaultContext());
    }

    @Override
    public void preFillFormData(final ShoppingList input, final AddWishlistLineItemFormData formData) {

    }
}
