package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.mappers;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.TermFacetedSearchFormSettings;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.TermFilterSearchModel;
import io.sphere.sdk.search.model.TypeSerializer;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.mappers.CategoryTreeMapperUtils.findCategoryIdentifierInPath;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public interface CategoryTreeFacetedSearchFormSettings extends SimpleCategoryTreeFacetedSearchFormSettings, TermFacetedSearchFormSettings<ProductProjection>, FormSettings<String> {

    @Override
    default String getDefaultValue() {
        return "";
    }

    @Nullable
    @Override
    default String mapFieldValueToValue(final String fieldValue) {
        return findSelectedCategory(fieldValue)
                .map(Resource::getId)
                .orElse(null);
    }

    @Override
    default boolean isValidValue(@Nullable final String value) {
        return value != null;
    }

    @Override
    default List<String> getSelectedValuesAsRawList(final Http.Context httpContext) {
        return findCategoryIdentifierInPath(httpContext)
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }

    @Override
    default List<FilterExpression<ProductProjection>> buildFilterExpressions(final Http.Context httpContext) {
        final String categoryId = getSelectedValue(httpContext);
        if (!categoryId.isEmpty()) {
            final String expression = String.format("%s: subtree(\"%s\")", getAttributePath(), categoryId);
            return singletonList(FilterExpression.of(expression));
        } else {
            return TermFilterSearchModel.<ProductProjection, String>of(getAttributePath(), TypeSerializer.ofString()).isIn(emptyList());
        }
    }

    Optional<Category> findSelectedCategory(final String categoryIdentifier);
}
