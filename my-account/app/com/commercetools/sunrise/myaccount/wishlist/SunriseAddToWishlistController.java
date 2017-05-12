package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.hooks.EnableHooks;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistLineItemFormData;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class SunriseAddToWishlistController extends SunriseContentFormController
        implements WithContentFormFlow<ShoppingList, ShoppingList, WishlistLineItemFormData> {
    private final WishlistLineItemFormData formData;
    private final WishlistFinderBySession wishlistFinder;
    private final AddToWishlistControllerAction controllerAction;
    private final WishlistReverseRouter reverseRouter;

    @Inject
    protected SunriseAddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                             final WishlistLineItemFormData formData,
                                             final WishlistFinderBySession wishlistFinder,
                                             final AddToWishlistControllerAction controllerAction, final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.reverseRouter = reverseRouter;
    }

    @Override
    public Class<? extends WishlistLineItemFormData> getFormDataClass() {
        return formData.getClass();
    }

    @EnableHooks
    @SunriseRoute(WishlistReverseRouter.ADD_TO_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(this::processForm, HttpExecution.defaultContext());
    }


    @Override
    public PageContent createPageContent(final ShoppingList input, final Form<? extends WishlistLineItemFormData> form) {
        return null;
    }

    @Override
    public void preFillFormData(final ShoppingList input, final WishlistLineItemFormData formData) {
        // not applicable here
    }

    @Override
    public CompletionStage<ShoppingList> executeAction(final ShoppingList input, final WishlistLineItemFormData formData) {
        return controllerAction.apply(input, formData);
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final WishlistLineItemFormData formData) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
