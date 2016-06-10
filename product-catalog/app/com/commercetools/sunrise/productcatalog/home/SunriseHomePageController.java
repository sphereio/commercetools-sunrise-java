package com.commercetools.sunrise.productcatalog.home;

import com.commercetools.sunrise.common.controllers.SunriseFrameworkController;
import com.commercetools.sunrise.common.controllers.SunrisePageData;
import com.commercetools.sunrise.common.hooks.RequestHook;
import com.commercetools.sunrise.common.hooks.SunrisePageDataHook;
import com.commercetools.sunrise.common.inject.RequestScoped;
import com.commercetools.sunrise.common.template.i18n.I18nIdentifier;
import com.commercetools.sunrise.common.template.i18n.I18nResolver;
import play.mvc.Result;
import play.twirl.api.Html;
import com.commercetools.sunrise.productcatalog.productsuggestions.ProductSuggestionsControllerComponent;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import static java.util.Arrays.asList;
import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Controller for the home page.
 <p>Components that may be a fit</p>
 * <ul>
 *     <li>{@link ProductSuggestionsControllerComponent}</li>
 * </ul>
 * <p id="hooks">supported hooks</p>
 * <ul>
 *     <li>{@link RequestHook}</li>
 *     <li>{@link SunrisePageDataHook}</li>
 * </ul>
 * <p>tags</p>
 * <ul>
 *     <li>home</li>
 *     <li>product-catalog</li>
 * </ul>
 */
@RequestScoped
public abstract class SunriseHomePageController extends SunriseFrameworkController {

    @Inject
    private I18nResolver i18nResolver;

    public CompletionStage<Result> show(final String languageTag) {
        return doRequest(() -> completedFuture(ok(renderHomePage(new HomePageContent()))));
    }

    protected Html renderHomePage(final HomePageContent pageContent) {
        pageContent.setAdditionalTitle(i18nResolver.getOrEmpty(userContext().locales(), I18nIdentifier.of("catalog:home.title")));
        final SunrisePageData pageData = pageData(pageContent);
        runVoidHook(SunrisePageDataHook.class, hook -> hook.acceptSunrisePageData(pageData));
        return templateEngine().renderToHtml("home", pageData, userContext().locales());
    }

    @Override
    public Set<String> getFrameworkTags() {
        return new HashSet<>(asList("home", "product-catalog"));
    }
}