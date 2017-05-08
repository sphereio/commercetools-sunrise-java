package controllers.myaccount;

import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.RemoveFromWishListFormData;
import com.commercetools.sunrise.myaccount.wishlist.RemoveFromWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseRemoveFromWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinder;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import play.data.FormFactory;

import javax.inject.Inject;

public class RemoveFromWishlistController extends SunriseRemoveFromWishlistController {
    @Inject
    protected RemoveFromWishlistController(final ContentRenderer contentRenderer, final FormFactory formFactory,
                                           final RemoveFromWishListFormData formData, final WishlistInSession wishlistInSession, final WishlistFinder wishlistFinder,
                                           final RemoveFromWishlistControllerAction controllerAction, final MyWishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, formData, wishlistInSession, wishlistFinder, controllerAction, reverseRouter);
    }
}
