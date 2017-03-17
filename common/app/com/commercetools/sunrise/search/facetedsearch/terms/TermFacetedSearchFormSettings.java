package com.commercetools.sunrise.search.facetedsearch.terms;

import com.commercetools.sunrise.framework.viewmodels.forms.FormSettings;
import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettingsWithOptions;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public interface TermFacetedSearchFormSettings<T> extends FacetedSearchFormSettingsWithOptions<T>, FormSettings<String> {

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
    default TermFacetedSearchExpression<T> buildFacetedSearchExpression(final Locale locale, final Http.Request httpRequest) {
        final TermFacetExpression<T> facetExpression = buildFacetExpression(locale);
        final List<FilterExpression<T>> filterExpressions = buildFilterExpressions(locale, httpRequest);
        return TermFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    @Override
    default List<FilterExpression<T>> buildFilterExpressions(final Locale locale, final Http.Request httpRequest) {
        final List<String> selectedValues = getAllSelectedValues(httpRequest);
        final FacetedSearchSearchModel<T> searchModel = TermFacetedSearchSearchModel.of(getLocalizedAttributePath(locale));
        final TermFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = searchModel.allTerms();
        } else if (isMatchingAll()) {
            facetedSearchExpr = searchModel.containsAll(selectedValues);
        } else {
            facetedSearchExpr = searchModel.containsAny(selectedValues);
        }
        return facetedSearchExpr.filterExpressions();
    }

    @Override
    default TermFacetExpression<T> buildFacetExpression(final Locale locale) {
        return TermFacetSearchModel.<T, String>of(getLocalizedAttributePath(locale), Function.identity())
                .withCountingProducts(true)
                .allTerms();
    }

    default Optional<TermFacetResult> findFacetResult(final PagedSearchResult<T> pagedSearchResult, final Locale locale) {
        final TermFacetExpression<T> facetExpression = buildFacetExpression(locale);
        return Optional.ofNullable(pagedSearchResult.getFacetResult(facetExpression));
    }

    static <T> TermFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String expression,
                                                   final boolean isCountDisplayed, @Nullable final String uiType,
                                                   final boolean isMultiSelect, final boolean isMatchingAll,
                                                   @Nullable final TermFacetMapperSettings mapper,
                                                   @Nullable final Long limit, @Nullable final Long threshold) {
        return new TermFacetedSearchFormSettingsImpl<>(fieldName, label, expression, isCountDisplayed, uiType, isMultiSelect, isMatchingAll, mapper, limit, threshold);
    }
}
