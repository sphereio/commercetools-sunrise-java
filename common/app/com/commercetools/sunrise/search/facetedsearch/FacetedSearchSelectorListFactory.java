package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import io.sphere.sdk.categories.Category;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestScoped
public class FacetedSearchSelectorListFactory {

    private final SelectFacetedSearchSelectorFactory selectFacetedSearchSelectorFactory;
    private final RangeFacetedSearchSelectorFactory rangeFacetedSearchSelectorFactory;
    private final FacetedSearchConfigList facetedSearchConfigList;

    @Inject
    public FacetedSearchSelectorListFactory(final SelectFacetedSearchSelectorFactory selectFacetedSearchSelectorFactory,
                                            final RangeFacetedSearchSelectorFactory rangeFacetedSearchSelectorFactory,
                                            final FacetedSearchConfigList facetedSearchConfigList) {
        this.selectFacetedSearchSelectorFactory = selectFacetedSearchSelectorFactory;
        this.rangeFacetedSearchSelectorFactory = rangeFacetedSearchSelectorFactory;
        this.facetedSearchConfigList = facetedSearchConfigList;
    }

    public List<FacetedSearchSelector> create(final List<Category> selectedCategories) {
        final List<FacetedSearchSelector> selectors = createSelectFacetSelectors(selectedCategories);
        selectors.addAll(createRangeFacetSelectors());
        return selectors;
    }

    private List<FacetedSearchSelector> createSelectFacetSelectors(final List<Category> selectedCategories) {
        return facetedSearchConfigList.getSelectFacetedSearchConfigList().stream()
                .map(facetConfig -> selectFacetedSearchSelectorFactory.create(facetConfig, selectedCategories))
                .collect(toList());
    }

    private List<FacetedSearchSelector> createRangeFacetSelectors() {
        return facetedSearchConfigList.getRangeFacetedSearchConfigList().stream()
                .map(rangeFacetedSearchSelectorFactory::create)
                .collect(toList());
    }
}
