package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.TermStats;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

/**
 * Mapper that transforms facet options with Category IDs into a hierarchical list of facet options, as defined by the given category list.
 * The IDs are then replaced by the Category name, in a language according to the provided locales.
 * Any facet option that is not represented in the list of categories or doesn't contain a name for the locales, is discarded.
 */
@RequestScoped
public class CategoryTreeTermFacetMapper implements TermFacetMapper {

    private final List<Locale> locales;
    private final Http.Context httpContext;
    private final CategoryTree projectCategoryTree;
    private final CategoryFinder categoryFinder;
    private final ProductReverseRouter productReverseRouter;

    @Inject
    protected CategoryTreeTermFacetMapper(final UserLanguage userLanguage, final Http.Context httpContext,
                                          final CategoryTree categoryTree, final CategoryFinder categoryFinder,
                                          final ProductReverseRouter productReverseRouter) {
        this.categoryFinder = categoryFinder;
        this.projectCategoryTree = categoryTree;
        this.locales = userLanguage.locales();
        this.productReverseRouter = productReverseRouter;
        this.httpContext = httpContext;
    }

    protected final List<Locale> getLocales() {
        return locales;
    }

    protected final Http.Context getHttpContext() {
        return httpContext;
    }

    protected final CategoryTree getProjectCategoryTree() {
        return projectCategoryTree;
    }

    protected final CategoryFinder getCategoryFinder() {
        return categoryFinder;
    }

    protected final ProductReverseRouter getProductReverseRouter() {
        return productReverseRouter;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult termFacetResult) {
        return findSelectedCategory(settings)
                .map(selectedCategory -> {
                    final CategoryTree subCategoryTree = buildSubCategoryTree(selectedCategory);
                    return subCategoryTree.getSubtreeRoots().stream()
                            .map(root -> createViewModel(root, termFacetResult, subCategoryTree, selectedCategory))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(toList());
                }).orElseGet(Collections::emptyList);
    }

    private Optional<FacetOptionViewModel> createViewModel(final Category category, final TermFacetResult termFacetResult,
                                                           final CategoryTree subCategoryTree, final Category selectedCategory) {
        return findTermStats(termFacetResult, category)
                .map(termStats -> {
                    final FacetOptionViewModel viewModel = createViewModel(category, termStats, selectedCategory);
                    subCategoryTree.findChildren(category)
                            .forEach(child -> createViewModel(child, termFacetResult, subCategoryTree, selectedCategory)
                                    .ifPresent(childViewModel -> {
                                        viewModel.getChildren().add(childViewModel);
                                        viewModel.setCount(viewModel.getCount() + childViewModel.getCount());
                                    }));
                    return viewModel;
        });
    }

    private FacetOptionViewModel createViewModel(final Category category, final TermStats termStats, final Category selectedCategory) {
        final FacetOptionViewModel viewModel = new FacetOptionViewModel();
        productReverseRouter.productOverviewPageCall(category).ifPresent(call -> viewModel.setValue(call.url()));
        viewModel.setLabel(category.getName().find(locales).orElseGet(category::getId));
        viewModel.setSelected(category.getId().equals(selectedCategory.getId()));
        viewModel.setCount(firstNonNull(termStats.getProductCount(), 0L));
        viewModel.setChildren(new ArrayList<>());
        return viewModel;
    }

    private Optional<TermStats> findTermStats(final TermFacetResult termFacetResult, final Category category) {
        return termFacetResult.getTerms().stream()
                .filter(termStats -> termStats.getTerm().equals(category.getId()))
                .findAny();
    }

    private Optional<Category> findSelectedCategory(final TermFacetedSearchFormSettings<?> settings) {
        final String categoryIdentifier = settings.getSelectedValue(httpContext);
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

    /**
     * Builds the category tree displayed in the category facet. That is, the tree containing:
     * - the category itself
     * - the ancestors tree
     * - the direct children categories
     * - the sibling categories
     * @param selectedCategory the selected category
     * @return the new category tree containing only those categories to be displayed in the category facet
     */
    protected CategoryTree buildSubCategoryTree(final Category selectedCategory) {
        final List<Category> categories = new ArrayList<>();
        categories.add(selectedCategory);
        categories.addAll(selectedCategory.getAncestors().stream()
                .map(ancestor -> projectCategoryTree.findById(ancestor.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(toList()));
        categories.addAll(projectCategoryTree.findChildren(selectedCategory));
        categories.addAll(projectCategoryTree.findSiblings(singletonList(selectedCategory)));
        return CategoryTree.of(categories);
    }
}
