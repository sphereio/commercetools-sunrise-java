package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.common.contexts.RequestContext;
import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.common.contexts.UserContextTestProvider;
import com.google.inject.Guice;
import io.sphere.sdk.facets.*;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.model.FacetRange;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static io.sphere.sdk.json.SphereJsonUtils.readObjectFromResource;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class RangeFacetedSearchSelectorFactoryTest {

    private static final String KEY = "key";
    private static final String LABEL = "Some Facet";

    @Test
    public void initializesFacet() throws Exception {
        final RangeFacetedSearchConfig config = RangeFacetedSearchConfig.of(facetBuilder("attr"), 1);
        final List<String> selectedValues = asList("0 to 1000", "1000 to 2000");
        test(config, selectedValues, facetSelector -> {
            final Facet<ProductProjection> facet = facetSelector.getFacet(searchResult());
            assertThat(facet).as("class").isInstanceOf(RangeFacet.class);
            final RangeFacet<ProductProjection> rangeFacet = (RangeFacet<ProductProjection>) facet;
            assertThat(rangeFacet.getType()).as("type").isEqualTo(SunriseFacetType.LIST);
            assertThat(rangeFacet.getKey()).as("key").isEqualTo(KEY);
            assertThat(rangeFacet.getLabel()).as("label").isEqualTo(LABEL);
            assertThat(rangeFacet.isCountHidden()).as("count hidden").isTrue();
            assertThat(rangeFacet.getSelectedRanges()).as("selected values").isEqualTo(asList(FilterRange.of("0", "1000"), FilterRange.of("1000", "2000")));
        });
    }

    @Test
    public void getsFacetedSearchExprWithSelectedValue() throws Exception {
        final RangeFacetedSearchConfig config = RangeFacetedSearchConfig.of(facetBuilder("attr"), 1);
        final List<String> selectedValues = asList("0 to 1000", "1000 to 2000");
        test(config, selectedValues, facetSelector -> {
            Assertions.assertThat(facetSelector.getFacetedSearchExpression().facetExpression().expression()).isEqualTo("attr:range(\"0\" to \"1000\")");
            Assertions.assertThat(facetSelector.getFacetedSearchExpression().filterExpressions()).extracting(FilterExpression::expression).containsOnly("attr:\"foo\"", "attr:\"bar\"");
        });
    }

    @Test
    public void getsFacetedSearchExprWithLocale() throws Exception {
        final RangeFacetedSearchConfig config = RangeFacetedSearchConfig.of(facetBuilder("some.{{locale}}.foo.{{locale}}.bar"), 1);
        final List<String> selectedValues = emptyList();
        test(config, selectedValues,  facetSelector -> {
            Assertions.assertThat(facetSelector.getFacetedSearchExpression().facetExpression().expression()).isEqualTo("some.en.foo.en.bar");
            Assertions.assertThat(facetSelector.getFacetedSearchExpression().filterExpressions()).isEmpty();
        });
    }

    private RangeFacetBuilder<ProductProjection> facetBuilder(final String attrPath) {
        return RangeFacetBuilder.of(KEY, RangeTermFacetedSearchSearchModel.<ProductProjection>of(attrPath))
                .label(LABEL)
                .type(SunriseFacetType.LIST)
                .countHidden(true)
                .initialRanges(asList(FacetRange.of("0", "1000")));
    }

    private void test(final RangeFacetedSearchConfig config, final List<String> selectedValues, final Consumer<FacetedSearchSelector> test) {
        final Map<String, List<String>> queryString = singletonMap(config.getFacetBuilder().getKey(), selectedValues);
        final UserContext userContext = Guice.createInjector().getInstance(UserContextTestProvider.class).get();
        final RequestContext requestContext = RequestContext.of(queryString, "");
        final RangeFacetedSearchSelectorFactory factory = RangeFacetedSearchSelectorFactory.of(config, userContext, requestContext);
        test.accept(factory.create());
    }

    private static PagedSearchResult<ProductProjection> searchResult() {
        return readObjectFromResource("search/pagedSearchResult.json", ProductProjectionSearch.resultTypeReference());
    }
}

