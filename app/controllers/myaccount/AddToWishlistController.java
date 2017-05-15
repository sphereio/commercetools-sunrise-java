package controllers.myaccount;

import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.AddToWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseAddToWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistLineItemFormData;
import play.data.FormFactory;

import javax.inject.Inject;

public class AddToWishlistController extends SunriseAddToWishlistController {

    @Inject
    public AddToWishlistController(final ContentRenderer contentRenderer,
                                   final FormFactory formFactory,
                                   final WishlistLineItemFormData formData,
                                   final WishlistFinderBySession wishlistFinder,
                                   final AddToWishlistControllerAction controllerAction,
                                   final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, formData, wishlistFinder, controllerAction, reverseRouter);
    }
}
