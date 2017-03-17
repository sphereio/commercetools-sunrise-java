package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperFactory;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@RequestScoped
public class CategoryTreeTermFacetMapperFactory implements TermFacetMapperFactory {

    private final CategoryTree categoryTree;
    private final List<Locale> locales;
    private final ProductReverseRouter productReverseRouter;
    private final Http.Context httpContext;
    private final CategoryFinder categoryFinder;

    @Inject
    public CategoryTreeTermFacetMapperFactory(final CategoryTree categoryTree, final UserLanguage userLanguage,
                                              final ProductReverseRouter productReverseRouter,
                                              final Http.Context httpContext, final CategoryFinder categoryFinder) {
        this.categoryTree = categoryTree;
        this.locales = userLanguage.locales();
        this.productReverseRouter = productReverseRouter;
        this.httpContext = httpContext;
        this.categoryFinder = categoryFinder;
    }

    @Override
    public CategoryTreeTermFacetMapper create(final TermFacetMapperSettings settings) {
        final Optional<Category> currentCategoryOpt = findCurrentCategory();
        final CategoryTree categoryTree = buildCategoryTreeForFacet(currentCategoryOpt.orElse(null));
        final List<Category> selectedCategories = currentCategoryOpt
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
        return new CategoryTreeTermFacetMapper(selectedCategories, categoryTree, locales, productReverseRouter);
    }

    /**
     * Builds the category tree displayed in the category facet. That is, the tree containing:
     * - the category itself
     * - the ancestors tree
     * - the direct children categories
     * - the sibling categories
     * In case there is no current category selected, then the whole category tree is returned
     * @param currentCategory the current selected category
     * @return the new category tree containing only those categories to be displayed in the category facet
     */
    private CategoryTree buildCategoryTreeForFacet(@Nullable final Category currentCategory) {
        if (currentCategory != null) {
            final List<Category> categories = new ArrayList<>();
            categories.add(currentCategory);
            categories.addAll(currentCategory.getAncestors().stream()
                    .map(c -> categoryTree.findById(c.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(toList()));
            categories.addAll(categoryTree.findChildren(currentCategory));
            categories.addAll(categoryTree.findSiblings(singletonList(currentCategory)));
            return CategoryTree.of(categories);
        } else {
            return categoryTree;
        }
    }

    private Optional<Category> findCurrentCategory() {
        return indexOfCategoryIdentifierInRoutePattern()
                .flatMap(index -> {
                    final String categoryIdentifier = httpContext.request().path().split("/")[index];
                    try {
                        return categoryFinder.apply(categoryIdentifier).toCompletableFuture().get(3, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        return Optional.empty();
                    }
                });
    }

    private Optional<Integer> indexOfCategoryIdentifierInRoutePattern() {
        return Optional.ofNullable(httpContext.args.get("ROUTE_PATTERN"))
                .map(routePattern -> routePattern.toString().replaceAll("<[^>]+>", "")) //hack since splitting '$languageTag<[^/]+>' with '/' would create more words
                .map(routePattern -> {
                    final List<String> paths = asList(routePattern.split("/"));
                    return paths.indexOf("$categoryIdentifier");
                })
                .filter(index -> index >= 0);
    }
}
