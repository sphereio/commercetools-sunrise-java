package controllers.wishlist;

import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.components.controllers.WishlistInSessionControllerComponent;
import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.AddToWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseAddToWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistCreator;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.AddWishlistLineItemFormData;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import io.sphere.sdk.shoppinglists.ShoppingList;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@RegisteredComponents(WishlistInSessionControllerComponent.class)
public class AddToWishlistController extends SunriseAddToWishlistController {
    private final WishlistReverseRouter reverseRouter;

    @Inject
    public AddToWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                   final WishlistPageContentFactory wishlistPageContentFactory,
                                   final AddWishlistLineItemFormData formData, final WishlistCreator wishlistCreator, final WishlistFinderBySession wishlistFinder,
                                   final AddToWishlistControllerAction controllerAction, final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, wishlistPageContentFactory, formData, wishlistCreator, wishlistFinder, controllerAction);
        this.reverseRouter = reverseRouter;
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final ShoppingList output, final AddWishlistLineItemFormData formData) {
        return redirectToCall(reverseRouter.wishlistPageCall());
    }
}
