package com.commercetools.sunrise.search.facetedsearch.mappers;

import com.commercetools.sunrise.search.facetedsearch.TermFacetedSearchFormOption;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.facets.FacetOption;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.TermFacetResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that transforms facet options with Category IDs into a hierarchical list of facet options, as defined by the given category list.
 * The IDs are then replaced by the Category name, in a language according to the provided locales.
 * Any facet option that is not represented in the list of categories or doesn't contain a name for the locales, is discarded.
 */
public class CategoryTreeFacetMapper implements FacetMapper {

    private final List<Category> selectedCategories;
    private final CategoryTree categoryTree;
    private final List<Locale> locales;

    private CategoryTreeFacetMapper(final List<Category> selectedCategories,
                                    final CategoryTree categoryTree, final List<Locale> locales) {
        this.selectedCategories = selectedCategories;
        this.categoryTree = categoryTree;
        this.locales = locales;
    }

    @Override
    public List<TermFacetedSearchFormOption> apply(final TermFacetResult termFacetResult) {
        return getRootCategories().stream()
                .map(root -> buildFacetOption(root, facetOptionFinder(facetOptions)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public static CategoryTreeFacetMapper of(final List<Category> selectedCategories,
                                             final CategoryTree subcategoryTree, final List<Locale> locales) {
        return new CategoryTreeFacetMapper(selectedCategories, subcategoryTree, locales);
    }

    private List<Category> getRootCategories() {
        return categoryTree.getAllAsFlatList().stream().filter(category -> {
            final Optional<Reference<Category>> parentRef = Optional.ofNullable(category.getParent());
            return parentRef.map(parent -> !categoryTree.findById(parent.getId()).isPresent()).orElse(true);
        }).collect(toList());
    }

    private Optional<FacetOption> buildFacetOption(final Category category, final Function<Category, Optional<FacetOption>> facetOptionFinder) {
        Optional<FacetOption> facetOption = facetOptionFinder.apply(category);
        final List<Category> children = categoryTree.findChildren(category);
        if (!children.isEmpty()) {
            facetOption = addChildrenToFacetOption(facetOption, category, children, facetOptionFinder);
        }
        final Optional<FacetOption> labelledOption = setNameToFacetOptionLabel(facetOption, category, locales);
        return setLinkToFacetOptionValue(labelledOption, category, locales);
    }

    private Optional<FacetOption> addChildrenToFacetOption(final Optional<FacetOption> facetOption,
                                                           final Category category, final List<Category> children,
                                                           final Function<Category, Optional<FacetOption>> facetOptionFinder) {
        boolean selected = selectedCategories.contains(category) || facetOption.map(FacetOption::isSelected).orElse(false);
        long count = facetOption.map(FacetOption::getCount).orElse(0L);
        List<FacetOption> childrenFacetOption = new ArrayList<>();

        for (final Category child : children) {
            final Optional<FacetOption> childFacetOption = buildFacetOption(child, facetOptionFinder);
            if (childFacetOption.isPresent()) {
                count += childFacetOption.get().getCount();
                childrenFacetOption.add(childFacetOption.get());
            }
        }
        return updateFacetOption(facetOption, category, selected, count, childrenFacetOption);
    }

    private Optional<FacetOption> updateFacetOption(final Optional<FacetOption> facetOption, final Category category,
                                                    final boolean selected, final long count, final List<FacetOption> childrenFacetOption) {
        FacetOption updatedFacetOption = null;
        if (facetOption.isPresent()) {
            updatedFacetOption = facetOption.get()
                    .withCount(count)
                    .withSelected(selected)
                    .withChildren(childrenFacetOption);
        } else if (!childrenFacetOption.isEmpty()) {
            updatedFacetOption = FacetOption.of(category.getId(), count, selected).withChildren(childrenFacetOption);
        }
        return Optional.ofNullable(updatedFacetOption);
    }

    private Optional<FacetOption> setNameToFacetOptionLabel(final Optional<FacetOption> facetOptionOptional,
                                                            final Category category, final List<Locale> locales) {
        return facetOptionOptional.flatMap(facetOption ->
                category.getName().find(locales)
                        .map(facetOption::withLabel));
    }

    private Optional<FacetOption> setLinkToFacetOptionValue(final Optional<FacetOption> facetOptionOptional,
                                                            final Category category, final List<Locale> locales) {
        return facetOptionOptional.flatMap(facetOption ->
                locales.stream().findFirst()
                        .flatMap(locale -> category.getSlug().find(locale)
                                .map(facetOption::withValue)));
    }

    private Function<Category, Optional<FacetOption>> facetOptionFinder(final List<FacetOption> facetOptions) {
        return category -> facetOptions.stream()
                .filter(facetOption -> facetOption.getValue().equals(category.getId()))
                .findFirst();
    }
}
