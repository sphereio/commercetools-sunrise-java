package controllers.wishlist;

import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.components.controllers.WishlistInSessionControllerComponent;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.ClearWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseClearWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.google.inject.Inject;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

@RegisteredComponents(WishlistInSessionControllerComponent.class)
public class ClearWishlistController extends SunriseClearWishlistController {

    private final WishlistReverseRouter reverseRouter;

    @Inject
    public ClearWishlistController(final ContentRenderer contentRenderer,
                                   final FormFactory formFactory,
                                   final WishlistFinderBySession wishlistFinder,
                                   final ClearWishlistControllerAction controllerAction,
                                   final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, wishlistFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }

    @Override
    public CompletionStage<Result> handleNotFoundWishlist() {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
