package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.SunriseWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistCreator;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinder;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import com.commercetools.sunrise.sessions.customer.CustomerOperationsControllerComponentSupplier;
import com.commercetools.sunrise.sessions.wishlist.WishlistInSession;
import com.google.inject.Inject;

@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        CustomerOperationsControllerComponentSupplier.class
})
public class MyWishListController extends SunriseWishlistController {

    @Inject
    public MyWishListController(final WishlistInSession wishlistInSession, final WishlistCreator wishlistCreator, final WishlistFinder wishlistFinder,
                                final ContentRenderer contentRenderer, final WishlistPageContentFactory wishlistPageContentFactory) {
        super(wishlistInSession, wishlistCreator, wishlistFinder, contentRenderer, wishlistPageContentFactory);
    }
}
