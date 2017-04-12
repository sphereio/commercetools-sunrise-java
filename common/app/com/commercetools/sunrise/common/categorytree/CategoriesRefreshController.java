package com.commercetools.sunrise.common.categorytree;

import com.commercetools.sunrise.categorytree.CategoryTreeConfiguration;
import com.commercetools.sunrise.categorytree.navigation.NavigationCategoryTreeConfiguration;
import com.commercetools.sunrise.framework.reverserouters.productcatalog.home.HomeReverseRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.cache.CacheApi;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class CategoriesRefreshController extends Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriesRefreshController.class);

    private final CategoryTreeConfiguration configuration;
    private final NavigationCategoryTreeConfiguration navigationConfiguration;
    private final CacheApi cacheApi;
    private final HomeReverseRouter homeReverseRouter;

    @Inject
    public CategoriesRefreshController(final CategoryTreeConfiguration configuration,
                                       final NavigationCategoryTreeConfiguration navigationConfiguration,
                                       final CacheApi cacheApi, final HomeReverseRouter homeReverseRouter) {
        this.configuration = configuration;
        this.navigationConfiguration = navigationConfiguration;
        this.cacheApi = cacheApi;
        this.homeReverseRouter = homeReverseRouter;
    }

    public Result refresh() {
        cacheApi.remove(configuration.cacheKey());
        cacheApi.remove(navigationConfiguration.cacheKey());
        LOGGER.info("Cached category trees removed");
        return redirect(homeReverseRouter.homePageCall());
    }
}
