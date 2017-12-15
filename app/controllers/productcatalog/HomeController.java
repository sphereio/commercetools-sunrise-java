package controllers.productcatalog;


import com.commercetools.sunrise.core.components.controllers.PageHeaderControllerComponentSupplier;
import com.commercetools.sunrise.core.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.core.controllers.cache.NoCache;
import com.commercetools.sunrise.core.controllers.metrics.LogMetrics;
import com.commercetools.sunrise.core.renderers.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.core.renderers.ContentRenderer;
import com.commercetools.sunrise.productcatalog.home.HomeRecommendationsControllerComponent;
import com.commercetools.sunrise.productcatalog.home.SunriseHomeController;
import com.commercetools.sunrise.productcatalog.home.viewmodels.HomePageContentFactory;
import com.commercetools.sunrise.wishlist.MiniWishlistControllerComponent;

import javax.inject.Inject;

@LogMetrics
@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        PageHeaderControllerComponentSupplier.class,
        HomeRecommendationsControllerComponent.class,
        MiniWishlistControllerComponent.class
})
public final class HomeController extends SunriseHomeController {

    @Inject
    public HomeController(final ContentRenderer contentRenderer,
                          final HomePageContentFactory pageContentFactory) {
        super(contentRenderer, pageContentFactory);
    }

    @Override
    public String getTemplateName() {
        return "home";
    }

    @Override
    public String getCmsPageKey() {
        return "home";
    }
}
