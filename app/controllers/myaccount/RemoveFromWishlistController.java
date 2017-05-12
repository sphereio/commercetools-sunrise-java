package controllers.myaccount;

import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.WishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.RemoveFromWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseRemoveFromWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistLineItemFormData;
import play.data.FormFactory;

import javax.inject.Inject;

public class RemoveFromWishlistController extends SunriseRemoveFromWishlistController {
    @Inject

    public RemoveFromWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory, final WishlistLineItemFormData formData,
                                        final WishlistFinderBySession wishlistFinder, final RemoveFromWishlistControllerAction controllerAction,
                                        final WishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, formData, wishlistFinder, controllerAction, reverseRouter);
    }
}
