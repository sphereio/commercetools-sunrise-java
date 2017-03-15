package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.RangeFacetExpression;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Function;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.parseFilterRange;

public interface SliderRangeFacetedSearchFormSettings<T> extends FacetedSearchFormSettings<T> {

    RangeEndpointFormSettings getLowerEndpointSettings();

    RangeEndpointFormSettings getUpperEndpointSettings();

    @Override
    default RangeFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale) {
        final String attributePath = getLocalizedAttributePath(locale);
        final RangeTermFacetedSearchSearchModel<T> searchModel = RangeTermFacetedSearchSearchModel.of(attributePath);
        final RangeFacetedSearchExpression<T> facetedSearchExpr = parseFilterRange(
                findSelectedValueFromQueryString(getLowerEndpointSettings(), httpRequest),
                findSelectedValueFromQueryString(getUpperEndpointSettings(), httpRequest))
                .map(searchModel::isBetween)
                .orElseGet(searchModel::allRanges);
        final RangeFacetExpression<T> facetExpression = RangeTermFacetSearchModel.<T, String>of(attributePath, Function.identity())
                .withCountingProducts(true)
                .allRanges();
        return RangeFacetedSearchExpression.of(facetExpression, facetedSearchExpr.filterExpressions());
    }

    static <T> SliderRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String attributePath,
                                                          final int position, final boolean isCountDisplayed, @Nullable final String uiType,
                                                          final RangeEndpointFormSettings lowerEndpointSettings, final RangeEndpointFormSettings upperEndpointSettings) {
        return new SliderRangeFacetedSearchFormSettingsImpl<>(fieldName, label, attributePath, position, isCountDisplayed, uiType, lowerEndpointSettings, upperEndpointSettings);
    }
}
