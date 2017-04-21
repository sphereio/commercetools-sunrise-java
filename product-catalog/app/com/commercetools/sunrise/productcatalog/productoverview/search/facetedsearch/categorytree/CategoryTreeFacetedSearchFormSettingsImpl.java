package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.categorytree.CategoryTreeConfiguration;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Collections.singletonList;

final class CategoryTreeFacetedSearchFormSettingsImpl extends AbstractFacetedSearchFormSettingsWithOptions<ConfiguredCategoryTreeFacetedSearchFormSettings> implements CategoryTreeFacetedSearchFormSettings {

    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryTreeFacetedSearchFormSettings.class);

    private final CategoryFinder categoryFinder;
    private final CategoryTreeConfiguration categoryTreeConfiguration;

    CategoryTreeFacetedSearchFormSettingsImpl(final ConfiguredCategoryTreeFacetedSearchFormSettings settings,
                                              final Locale locale, final CategoryFinder categoryFinder,
                                              final CategoryTreeConfiguration categoryTreeConfiguration) {
        super(settings, locale);
        this.categoryFinder = categoryFinder;
        this.categoryTreeConfiguration = categoryTreeConfiguration;
    }

    @Override
    public String getFieldName() {
        return configuration().getFieldName();
    }

    @Override
    public Optional<Category> mapFieldValueToValue(final String categoryIdentifier) {
        if (!categoryIdentifier.isEmpty()) {
            try {
                return categoryFinder.apply(categoryIdentifier)
                        .toCompletableFuture().get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                LOGGER.debug("Could not find category", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FilterExpression<ProductProjection>> buildFilterExpressions(final Http.Context httpContext) {
        return categoryTreeConfiguration.onSaleExtId()
                .flatMap(onSaleExtId -> getSelectedValue(httpContext)
                        .filter(category -> onSaleExtId.equals(category.getExternalId()))
                        .map(category -> singletonList(FilterExpression.<ProductProjection>of(categoryTreeConfiguration.onSaleExpression()))))
                .orElseGet(() -> CategoryTreeFacetedSearchFormSettings.super.buildFilterExpressions(httpContext));
    }
}
