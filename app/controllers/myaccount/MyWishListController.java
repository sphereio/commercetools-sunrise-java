package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.MyWishlistCreator;
import com.commercetools.sunrise.myaccount.wishlist.SunriseWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import com.commercetools.sunrise.sessions.customer.CustomerOperationsControllerComponentSupplier;
import com.google.inject.Inject;

@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        CustomerOperationsControllerComponentSupplier.class
})
public class MyWishListController extends SunriseWishlistController {

    @Inject
    public MyWishListController(final MyWishlistCreator wishlistCreator, final ContentRenderer contentRenderer, final WishlistPageContentFactory wishlistPageContentFactory) {
        super(wishlistCreator, contentRenderer, wishlistPageContentFactory);
    }
}
