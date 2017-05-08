package com.commercetools.sunrise.myaccount.wishlist;

import com.commercetools.sunrise.framework.controllers.SunriseContentFormController;
import com.commercetools.sunrise.framework.controllers.WithContentFormFlow;
import com.commercetools.sunrise.framework.reverserouters.SunriseRoute;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.shoppingcart.cart.CartReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.framework.viewmodels.content.PageContent;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SunriseAddToWishlistController extends SunriseContentFormController
        implements WithContentFormFlow<ShoppingList, ShoppingList, AddToWishlistFormData> {

    private final AddToWishlistFormData formData;
    private final WishlistInSession wishlistInSession;
    private final WishlistCreator wishlistCreator;
    private final WishlistFinder wishlistFinder;
    private final AddToWishlistControllerAction controllerAction;
    private final MyWishlistReverseRouter reverseRouter;

    @Inject
    protected SunriseAddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                             final AddToWishlistFormData formData,
                                             final WishlistInSession wishlistInSession, final WishlistCreator wishlistCreator,
                                             final WishlistFinder wishlistFinder,
                                             final AddToWishlistControllerAction controllerAction, final MyWishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory);
        this.formData = formData;
        this.wishlistInSession = wishlistInSession;
        this.wishlistCreator = wishlistCreator;
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
        return wishlistInSession.findWishlistId()
                .map(wishlistFinder::findById)
                .orElseGet(() -> wishlistCreator.get())
                .thenComposeAsync(this::storeInSession, HttpExecution.defaultContext())
                .thenComposeAsync(this::processForm, HttpExecution.defaultContext());
    }

    private CompletionStage<ShoppingList> storeInSession(final ShoppingList input) {
        wishlistInSession.store(input);
        return CompletableFuture.completedFuture(input);
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
