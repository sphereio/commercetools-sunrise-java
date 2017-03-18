package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.AbstractConfigurableFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import io.sphere.sdk.products.ProductProjection;
import play.Configuration;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public final class SimpleCategoryTreeFacetedSearchFormSettings extends AbstractConfigurableFacetedSearchFormSettings<ProductProjection> implements SimpleTermFacetedSearchFormSettings<ProductProjection> {

    private final String fieldName;
    private final TermFacetMapperSettings mapperSettings;

    @Inject
    public SimpleCategoryTreeFacetedSearchFormSettings(final Configuration configuration) {
        super(configuration);
        this.fieldName = fieldName(configuration);
        this.mapperSettings = TermFacetMapperSettings.of(ProductTermFacetMapperType.CATEGORY_TREE, null);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean isMultiSelect() {
        return false;
    }

    @Override
    public boolean isMatchingAll() {
        return false;
    }

    @Nullable
    @Override
    public Long getThreshold() {
        return null;
    }

    @Nullable
    @Override
    public Long getLimit() {
        return null;
    }

    @Nullable
    @Override
    public TermFacetMapperSettings getMapperSettings() {
        return mapperSettings;
    }

    @Override
    public List<String> getAllSelectedValues(final Http.Context httpContext) {
        return singletonList(getSelectedValue(httpContext));
    }

    @Override
    public String getSelectedValue(final Http.Context httpContext) {
        return findCurrentCategoryIdentifier(httpContext).orElse("");
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
