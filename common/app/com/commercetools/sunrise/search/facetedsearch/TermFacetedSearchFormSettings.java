package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import io.sphere.sdk.search.TermFacetedSearchExpression;
import io.sphere.sdk.search.model.FacetedSearchSearchModel;
import io.sphere.sdk.search.model.TermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;

public interface TermFacetedSearchFormSettings<T> extends FacetedSearchFormSettings<T> {

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

    @Override
    default TermFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale) {
        final FacetedSearchSearchModel<T> searchModel = TermFacetedSearchSearchModel.of(getLocalizedExpression(locale));
        final List<String> selectedValues = findAllSelectedValuesFromQueryString(getFieldName(), httpRequest);
        final TermFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = searchModel.allTerms();
        } else if (isMatchingAll()) {
            facetedSearchExpr = searchModel.containsAll(selectedValues);
        } else {
            facetedSearchExpr = searchModel.containsAny(selectedValues);
        }
        return facetedSearchExpr;
    }

    static <T> TermFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String expression,
                                                   final int position, final FacetUIType uiType, final boolean isCountDisplayed,
                                                   final boolean isMultiSelect, final boolean isMatchingAll, @Nullable final FacetMapperSettings mapper,
                                                   @Nullable final Long limit, @Nullable final Long threshold) {
        return new TermFacetedSearchFormSettingsImpl<>(fieldName, label, expression, position, uiType, isCountDisplayed, isMultiSelect, isMatchingAll, mapper, limit, threshold);
    }
}
