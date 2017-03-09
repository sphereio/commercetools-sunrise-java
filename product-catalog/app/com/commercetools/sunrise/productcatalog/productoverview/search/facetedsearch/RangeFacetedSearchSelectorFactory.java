package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.common.contexts.RequestContext;
import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.productcatalog.productoverview.search.SearchUtils;
import io.sphere.sdk.facets.*;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SearchModel;

import java.util.ArrayList;
import java.util.List;

import static com.commercetools.sunrise.productcatalog.productoverview.search.SearchUtils.localizeExpression;

public class RangeFacetedSearchSelectorFactory extends FacetedSearchSelectorFactory<RangeFacetBuilder<ProductProjection>> {

    private final RangeFacetedSearchConfig config;
    private final UserContext userContext;
    private final RequestContext requestContext;

    protected RangeFacetedSearchSelectorFactory(final RangeFacetedSearchConfig facetConfig, final UserContext userContext,
                                                 final RequestContext requestContext) {
        super(facetConfig);
        this.config = facetConfig;
        this.userContext = userContext;
        this.requestContext = requestContext;
    }

    public static RangeFacetedSearchSelectorFactory of(final RangeFacetedSearchConfig facetConfig, final UserContext userContext,
                                                        final RequestContext requestContext) {
        return new RangeFacetedSearchSelectorFactory(facetConfig, userContext, requestContext);
    }

    protected List<FilterRange<String>> selectedRanges() {
        List<String> selectedRange = SearchUtils.selectedValues(key(), requestContext);
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

    @Override
    protected Facet<ProductProjection> initializeFacet() {
        return config.getFacetBuilder()
                .rangeTermFacetedSearchSearchModel(localizedSearchModel())
                .selectedRanges(selectedRanges())
                .build();
    }

    protected RangeTermFacetedSearchSearchModel<ProductProjection> localizedSearchModel() {
        final SearchModel<ProductProjection> searchModel = config.getFacetBuilder().getRangeTermFacetedSearchSearchModel().getSearchModel();
        final String localizedAttrPath = localizeExpression(searchModel.attributePath(), userContext.locale());
        return RangeTermFacetedSearchSearchModel.of(localizedAttrPath);
    }
}
