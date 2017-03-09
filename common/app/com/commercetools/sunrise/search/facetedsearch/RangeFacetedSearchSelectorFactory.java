package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.facets.Facet;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;
import static com.commercetools.sunrise.search.SearchUtils.localizeExpression;

public class RangeFacetedSearchSelectorFactory extends Base {

    private final Locale locale;
    private final Http.Request httpRequest;

    @Inject
    public RangeFacetedSearchSelectorFactory(final Locale locale, final Http.Request httpRequest) {
        this.locale = locale;
        this.httpRequest = httpRequest;
    }

    public FacetedSearchSelector create(final RangeFacetedSearchConfig facetConfig) {
        final Facet<ProductProjection> facet = initializeFacet(facetConfig);
        return FacetedSearchSelector.of(facet, facetConfig.getPosition());
    }

    private Facet<ProductProjection> initializeFacet(final RangeFacetedSearchConfig facetConfig) {
        return facetConfig.getFacetBuilder()
                .rangeTermFacetedSearchSearchModel(localizedSearchModel(facetConfig))
                .selectedRanges(getSelectedRanges(facetConfig))
                .build();
    }

    private List<FilterRange<String>> getSelectedRanges(final RangeFacetedSearchConfig facetConfig) {
        List<String> selectedRange = findAllSelectedValuesFromQueryString(facetConfig.getFacetBuilder().getKey(), httpRequest);
        List<FilterRange<String>> result = new ArrayList<>();
        for (String initialRange : selectedRange) {
            String[] tempArr = initialRange.split(" to ");
            if (tempArr[0].isEmpty()) {
                result.add(FilterRange.atMost(tempArr[1]));
            } else if (tempArr.length == 1) {
                result.add(FilterRange.atLeast(tempArr[0]));
            } else {
                result.add(FilterRange.of(tempArr[0], tempArr[1]));
            }
        }
        return result;
    }

    private RangeTermFacetedSearchSearchModel<ProductProjection> localizedSearchModel(final RangeFacetedSearchConfig facetConfig) {
        final SearchModel<ProductProjection> searchModel = facetConfig.getFacetBuilder().getRangeTermFacetedSearchSearchModel().getSearchModel();
        final String localizedAttrPath = localizeExpression(searchModel.attributePath(), locale);
        return RangeTermFacetedSearchSearchModel.of(localizedAttrPath);
    }
}
