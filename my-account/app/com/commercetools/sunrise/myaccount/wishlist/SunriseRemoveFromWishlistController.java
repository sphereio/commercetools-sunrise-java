package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class SunriseRemoveFromWishlistController extends SunriseContentFormController
        implements WithContentFormFlow<ShoppingList, ShoppingList, RemoveFromWishListFormData> {
    private final RemoveFromWishListFormData formData;
    private final WishlistFinderBySession wishlistFinder;
    private final RemoveFromWishlistControllerAction controllerAction;
    private final MyWishlistReverseRouter reverseRouter;

    @Inject
    protected SunriseRemoveFromWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory, final RemoveFromWishListFormData formData,
                                                  final WishlistFinderBySession wishlistFinder, final RemoveFromWishlistControllerAction controllerAction,
                                                  final MyWishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.reverseRouter = reverseRouter;
    }

    @SunriseRoute(MyWishlistReverseRouter.REMOVE_FROM_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(this::processForm, HttpExecution.defaultContext());
    }

    @Override
    public Class<? extends RemoveFromWishListFormData> getFormDataClass() {
        return formData.getClass();
    }

    @Override
    public PageContent createPageContent(final ShoppingList input, final Form<? extends RemoveFromWishListFormData> form) {
        return null;
    }

    @Override
    public void preFillFormData(final ShoppingList input, final RemoveFromWishListFormData formData) {
        // not applicable here
    }

    @Override
    public CompletionStage<ShoppingList> executeAction(final ShoppingList input, final RemoveFromWishListFormData formData) {
        return controllerAction.apply(input, formData);
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final RemoveFromWishListFormData formData) {
        return redirectToCall(reverseRouter.myWishlistPageCall("en"));
    }
}
