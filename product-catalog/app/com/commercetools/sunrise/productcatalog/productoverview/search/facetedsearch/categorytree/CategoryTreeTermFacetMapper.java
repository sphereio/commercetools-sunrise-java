package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapper;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.TermStats;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that transforms facet options with Category IDs into a hierarchical list of facet options, as defined by the given category list.
 * The IDs are then replaced by the Category name, in a language according to the provided locales.
 * Any facet option that is not represented in the list of categories or doesn't contain a name for the locales, is discarded.
 */
@RequestScoped
public final class CategoryTreeTermFacetMapper implements TermFacetMapper {

    private final Http.Context httpContext;
    private final CategoryTree categoryTree;
    private final CategoryFacetOptionViewModelFactory categoryFacetOptionViewModelFactory;

    @Inject
    CategoryTreeTermFacetMapper(final Http.Context httpContext, final CategoryTree categoryTree,
                                final CategoryFacetOptionViewModelFactory categoryFacetOptionViewModelFactory) {
        this.httpContext = httpContext;
        this.categoryTree = categoryTree;
        this.categoryFacetOptionViewModelFactory = categoryFacetOptionViewModelFactory;
    }

    @Override
    public List<FacetOptionViewModel> apply(final TermFacetedSearchFormSettings<?> settings, final TermFacetResult termFacetResult) {
        final String selectedValue = settings.getSelectedValue(httpContext);
        return categoryTree.getRoots().stream()
                .map(root -> createViewModel(root, termFacetResult, selectedValue))
                .filter(root -> root.getCount() > 0)
                .collect(toList());
    }

    private FacetOptionViewModel createViewModel(final Category category, final TermFacetResult termFacetResult, final String selectedValue) {
        final TermStats termStats = findTermStats(category, termFacetResult);
        final FacetOptionViewModel viewModel = categoryFacetOptionViewModelFactory.create(termStats, category, selectedValue);
        final List<FacetOptionViewModel> childrenViewModels = new ArrayList<>();
        categoryTree.findChildren(category)
                .forEach(child -> {
                    final FacetOptionViewModel childViewModel = createViewModel(child, termFacetResult, selectedValue);
                    if (childViewModel.getCount() > 0) {
                        childrenViewModels.add(childViewModel);
                        viewModel.setCount(viewModel.getCount() + childViewModel.getCount());
                        viewModel.setSelected(viewModel.isSelected() || childViewModel.isSelected());
                    }
                });
        if (viewModel.isSelected()) {
            viewModel.setChildren(childrenViewModels);
        }
        return viewModel;
    }

    @Nullable
    private TermStats findTermStats(final Category category, final TermFacetResult termFacetResult) {
        return termFacetResult.getTerms().stream()
                .filter(stats -> stats.getTerm().equals(category.getId()))
                .findAny()
                .orElse(null);
    }


}
