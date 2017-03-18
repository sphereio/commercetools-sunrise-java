package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.AbstractFacetedSearchFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.TermFilterSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public final class CategoryTreeFacetedSearchFormSettings extends AbstractFacetedSearchFormSettingsWithOptions<ProductProjection, SimpleCategoryTreeFacetedSearchFormSettings> implements TermFacetedSearchFormSettings<ProductProjection> {

    private final CategoryFinder categoryFinder;

    public CategoryTreeFacetedSearchFormSettings(final SimpleCategoryTreeFacetedSearchFormSettings settings, final Locale locale,
                                                 final CategoryFinder categoryFinder) {
        super(settings, locale);
        this.categoryFinder = categoryFinder;
    }

    @Override
    public String getFieldName() {
        return getSettings().getFieldName();
    }

    @Nullable
    @Override
    public Long getThreshold() {
        return getSettings().getThreshold();
    }

    @Nullable
    @Override
    public Long getLimit() {
        return getSettings().getLimit();
    }

    @Nullable
    @Override
    public TermFacetMapperSettings getMapperSettings() {
        return getSettings().getMapperSettings();
    }

    @Override
    public List<String> getAllSelectedValues(final Http.Context httpContext) {
        return singletonList(findCurrentCategoryIdentifier(httpContext).orElse(""));
    }

    @Override
    public List<FilterExpression<ProductProjection>> buildFilterExpressions(final Http.Context httpContext) {
        return findSelectedCategory(httpContext)
                .map(category -> String.format("%s:subtree(\"%s\")", getAttributePath(), category.getId()))
                .map(FilterExpression::<ProductProjection>of)
                .map(Collections::singletonList)
                .orElseGet(() -> TermFilterSearchModel.<ProductProjection, String>of(getAttributePath(), Function.identity()).isIn(emptyList()));
    }

    private Optional<Category> findSelectedCategory(final Http.Context httpContext) {
        final String categoryIdentifier = getSelectedValue(httpContext);
        if (!categoryIdentifier.isEmpty()) {
            try {
                return categoryFinder.apply(categoryIdentifier)
                        .toCompletableFuture().get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                // Returns default empty
            }
        }
        return Optional.empty();
    }

    private Optional<String> findCurrentCategoryIdentifier(final Http.Context httpContext) {
        return Optional.ofNullable(httpContext.args.get("ROUTE_PATTERN"))
                .map(routePattern -> routePattern.toString().replaceAll("<[^>]+>", "")) //hack since splitting '$categoryIdentifier<[^/]+>' with '/' would create more words
                .map(routePattern -> {
                    final List<String> paths = asList(routePattern.split("/"));
                    return paths.indexOf("$categoryIdentifier");
                })
                .filter(index -> index >= 0)
                .map(index -> httpContext.request().path().split("/")[index]);
    }
}
