package controllers.myaccount;

import com.commercetools.sunrise.framework.reverserouters.myaccount.wishlist.MyWishlistReverseRouter;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.*;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import com.google.inject.Inject;
import play.data.FormFactory;

public class AddToWishlistController extends SunriseAddToWishlistController {

    @Inject
    public AddToWishlistController(final ContentRenderer contentRenderer,
                                   final FormFactory formFactory, final AddToWishlistFormData formData, final WishlistInSession wishlistInSession,
                                   final WishlistCreator wishlistCreator, final WishlistFinder wishlistFinder,
                                   final AddToWishlistControllerAction controllerAction, final MyWishlistReverseRouter reverseRouter) {
        super(contentRenderer, formFactory, formData, wishlistInSession, wishlistCreator, wishlistFinder, controllerAction, reverseRouter);
    }
}
