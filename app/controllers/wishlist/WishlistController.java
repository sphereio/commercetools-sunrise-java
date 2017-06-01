package controllers.wishlist;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.components.controllers.WishlistInSessionControllerComponent;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.ClearWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;

import javax.inject.Inject;

@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        WishlistInSessionControllerComponent.class
})
public class WishlistController extends SunriseWishlistController {

    @Inject
    public WishlistController(final ContentRenderer contentRenderer,
                              final WishlistFinderBySession wishlistFinder,
                              final ClearWishlistControllerAction controllerAction,
                              final WishlistPageContentFactory wishlistPageContentFactory) {
        super(wishlistFinder, controllerAction, contentRenderer, wishlistPageContentFactory);
    }
}
