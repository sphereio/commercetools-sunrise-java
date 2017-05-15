package controllers.myaccount;

import com.commercetools.sunrise.framework.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.myaccount.wishlist.ClearWishlistControllerAction;
import com.commercetools.sunrise.myaccount.wishlist.SunriseWishlistController;
import com.commercetools.sunrise.myaccount.wishlist.WishlistFinderBySession;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistPaginationControllerComponent;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistPaginationSettings;
import com.commercetools.sunrise.myaccount.wishlist.pagination.WishlistProductsPerPageFormSettings;
import com.commercetools.sunrise.myaccount.wishlist.viewmodels.WishlistPageContentFactory;
import com.commercetools.sunrise.sessions.customer.CustomerOperationsControllerComponentSupplier;

import javax.inject.Inject;

@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        CustomerOperationsControllerComponentSupplier.class,
        WishlistPaginationControllerComponent.class
})
public class MyWishlistController extends SunriseWishlistController {

    @Inject
    public MyWishlistController(final ContentRenderer contentRenderer,
                                final WishlistFinderBySession wishlistFinder,
                                final ClearWishlistControllerAction controllerAction,
                                final WishlistPageContentFactory wishlistPageContentFactory,
                                final WishlistPaginationSettings paginationSettings,
                                final WishlistProductsPerPageFormSettings entriesPerPageFormSettings) {
        super(wishlistFinder, controllerAction, contentRenderer, wishlistPageContentFactory, paginationSettings, entriesPerPageFormSettings);
    }
}
