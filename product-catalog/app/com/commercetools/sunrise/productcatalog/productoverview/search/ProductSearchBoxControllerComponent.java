package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.components.controllers.ControllerComponent;
import com.commercetools.sunrise.framework.hooks.requests.ProductProjectionSearchHook;
import com.commercetools.sunrise.search.searchbox.AbstractSearchBoxControllerComponent;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Locale;

public final class ProductSearchBoxControllerComponent extends AbstractSearchBoxControllerComponent implements ControllerComponent, ProductProjectionSearchHook {

    @Inject
    public ProductSearchBoxControllerComponent(final ProductSearchBoxSettings productSearchBoxSettings,
                                               final Http.Request httpRequest, final Locale locale) {
        super(productSearchBoxSettings, httpRequest, locale);
    }

    @Override
    public ProductProjectionSearch onProductProjectionSearch(final ProductProjectionSearch search) {
        final LocalizedStringEntry searchText = getSearchText();
        if (!searchText.getValue().isEmpty()) {
            return search.withText(searchText);
        } else {
            return search;
        }
    }
}
