package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.TermStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

/**
 * Mapper that transforms facet options with Category IDs into a hierarchical list of facet options, as defined by the given category list.
 * The IDs are then replaced by the Category name, in a language according to the provided locales.
 * Any facet option that is not represented in the list of categories or doesn't contain a name for the locales, is discarded.
 */
public final class CategoryTreeTermFacetMapper implements TermFacetMapper {

    private final List<Category> selectedCategories;
    private final CategoryTree categoryTree;
    private final List<Locale> locales;
    private final ProductReverseRouter productReverseRouter;

    CategoryTreeTermFacetMapper(final List<Category> selectedCategories, final CategoryTree categoryTree,
                                final List<Locale> locales, final ProductReverseRouter productReverseRouter) {
        this.selectedCategories = selectedCategories;
        this.categoryTree = categoryTree;
        this.locales = locales;
        this.productReverseRouter = productReverseRouter;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetResult termFacetResult, final List<String> selectedValues) {
        return categoryTree.getSubtreeRoots().stream()
                .map(root -> createViewModel(root, termFacetResult))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    private Optional<FacetOptionViewModel> createViewModel(final Category category, final TermFacetResult termFacetResult) {
        return findTermStats(termFacetResult, category)
                .map(termStats -> {
                    final FacetOptionViewModel viewModel = createViewModel(category, termStats);
                    categoryTree.findChildren(category)
                            .forEach(child -> createViewModel(child, termFacetResult)
                                    .ifPresent(childViewModel -> {
                                        viewModel.getChildren().add(childViewModel);
                                        viewModel.setCount(viewModel.getCount() + childViewModel.getCount());
                                    }));
                    return viewModel;
        });
    }

    private FacetOptionViewModel createViewModel(final Category category, final TermStats termStats) {
        final FacetOptionViewModel viewModel = new FacetOptionViewModel();
        productReverseRouter.productOverviewPageCall(category).ifPresent(call -> viewModel.setValue(call.url()));
        viewModel.setLabel(category.getName().find(locales).orElseGet(category::getId));
        viewModel.setSelected(selectedCategories.contains(category));
        viewModel.setCount(firstNonNull(termStats.getProductCount(), 0L));
        viewModel.setChildren(new ArrayList<>());
        return viewModel;
    }

    private Optional<TermStats> findTermStats(final TermFacetResult termFacetResult, final Category category) {
        return termFacetResult.getTerms().stream()
                .filter(termStats -> termStats.getTerm().equals(category.getId()))
                .findAny();
    }
}
