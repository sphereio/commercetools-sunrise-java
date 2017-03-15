package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import com.commercetools.sunrise.search.facetedsearch.mappers.TermFacetMapperSettings;
import io.sphere.sdk.search.TermFacetExpression;
import io.sphere.sdk.search.TermFacetedSearchExpression;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public interface TermFacetedSearchFormSettings<T> extends MultiOptionFacetedSearchFormSettings<T>, FormSettings<String> {

    /**
     * Gets the threshold indicating the minimum amount of options allowed to be displayed in the facet.
     * @return the threshold for the amount of options that can be displayed, or absent if it has no threshold
     */
    @Nullable
    Long getThreshold();

    /**
     * Gets the limit for the maximum amount of options allowed to be displayed in the facet.
     * @return the limit for the amount of options that can be displayed, or absent if it has no limit
     */
    @Nullable
    Long getLimit();

    /**
     * Gets the mapper type for this facet.
     * @return the facet option mapper, or absent if there is no mapper
     */
    @Nullable
    TermFacetMapperSettings getMapperSettings();

    @Override
    default String getDefaultValue() {
        return "";
    }

    @Override
    default String mapToValue(final String valueAsString) {
        return valueAsString;
    }

    @Override
    default boolean isValidValue(final String value) {
        return true;
    }

    @Override
    default TermFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale) {
        final String attributePath = getLocalizedAttributePath(locale);
        final FacetedSearchSearchModel<T> searchModel = TermFacetedSearchSearchModel.of(attributePath);
        final List<String> selectedValues = getAllSelectedValues(httpRequest);
        final TermFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = searchModel.allTerms();
        } else if (isMatchingAll()) {
            facetedSearchExpr = searchModel.containsAll(selectedValues);
        } else {
            facetedSearchExpr = searchModel.containsAny(selectedValues);
        }
        final TermFacetExpression<T> facetExpression = TermFacetSearchModel.<T, String>of(attributePath, Function.identity())
                .withCountingProducts(true)
                .allTerms();
        return TermFacetedSearchExpression.of(facetExpression, facetedSearchExpr.filterExpressions());
    }

    static <T> TermFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String expression, final int position,
                                                   final boolean isCountDisplayed, @Nullable final String uiType,
                                                   final boolean isMultiSelect, final boolean isMatchingAll,
                                                   @Nullable final TermFacetMapperSettings mapper,
                                                   @Nullable final Long limit, @Nullable final Long threshold) {
        return new TermFacetedSearchFormSettingsImpl<>(fieldName, label, expression, position, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, mapper, limit, threshold);
    }
}
