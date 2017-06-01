package controllers.wishlist;

import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.components.controllers.WishlistInSessionControllerComponent;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.RemoveFromWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseRemoveFromWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.RemoveWishlistLineItemFormData;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@RegisteredComponents(WishlistInSessionControllerComponent.class)
public class RemoveFromWishlistController extends SunriseRemoveFromWishlistController {
    private final WishlistReverseRouter reverseRouter;

    @Inject
    public RemoveFromWishlistController(final ContentRenderer contentRenderer,
                                        final FormFactory formFactory,
                                        final WishlistPageContentFactory wishlistPageContentFactory,
                                        final RemoveWishlistLineItemFormData formData,
                                        final WishlistFinderBySession wishlistFinder,
                                        final RemoveFromWishlistControllerAction controllerAction,
                                        final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, wishlistPageContentFactory, formData, wishlistFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final RemoveWishlistLineItemFormData formData) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }

    @Override
    public CompletionStage<Result> handleNotFoundWishlist() {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
