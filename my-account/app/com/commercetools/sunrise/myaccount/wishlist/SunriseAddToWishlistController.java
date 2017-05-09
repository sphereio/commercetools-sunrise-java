package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class SunriseAddToWishlistController extends SunriseContentFormController
        implements WithContentFormFlow<ShoppingList, ShoppingList, AddToWishlistFormData> {
    private final AddToWishlistFormData formData;
    private final WishlistFinderBySession wishlistFinder;
    private final AddToWishlistControllerAction controllerAction;
    private final MyWishlistReverseRouter reverseRouter;

    @Inject
    protected SunriseAddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                             final AddToWishlistFormData formData,
                                             final WishlistFinderBySession wishlistFinder,
                                             final AddToWishlistControllerAction controllerAction, final MyWishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.wishlistFinder = wishlistFinder;
        this.controllerAction = controllerAction;
        this.reverseRouter = reverseRouter;
    }

    @Override
    public Class<? extends AddToWishlistFormData> getFormDataClass() {
        return formData.getClass();
    }

    @SunriseRoute(MyWishlistReverseRouter.ADD_TO_WISHLIST_PROCESS)
    public CompletionStage<Result> process(final String languageTag) {
        return wishlistFinder.getOrCreate()
                .thenComposeAsync(this::processForm, HttpExecution.defaultContext());
    }


    @Override
    public PageContent createPageContent(final ShoppingList input, final Form<? extends AddToWishlistFormData> form) {
        return null;
    }

    @Override
    public void preFillFormData(final ShoppingList input, final AddToWishlistFormData formData) {
        // not applicable here
    }

    @Override
    public CompletionStage<ShoppingList> executeAction(final ShoppingList input, final AddToWishlistFormData formData) {
        return controllerAction.apply(input, formData);
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final AddToWishlistFormData formData) {
        return redirectToCall(reverseRouter.myWishlistPageCall("en"));
    }
}
