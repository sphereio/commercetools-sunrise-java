package com.commercetools.sunrise.search.facetedsearch.sliderranges;

import com.commercetools.sunrise.search.facetedsearch.FacetedSearchFormSettings;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetExpression;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SimpleRangeStats;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findSelectedValueFromQueryString;
import static com.commercetools.sunrise.search.facetedsearch.RangeUtils.parseFilterRange;

public interface SliderRangeFacetedSearchFormSettings<T> extends FacetedSearchFormSettings<T> {

    RangeEndpointFormSettings getLowerEndpointSettings();

    RangeEndpointFormSettings getUpperEndpointSettings();

    @Override
    default RangeFacetedSearchExpression<T> buildFacetedSearchExpression(final Locale locale, final Http.Request httpRequest) {
        final RangeFacetExpression<T> facetExpression = buildFacetExpression(locale);
        final List<FilterExpression<T>> filterExpressions = buildFilterExpressions(locale, httpRequest);
        return RangeFacetedSearchExpression.of(facetExpression, filterExpressions);
    }

    @Override
    default List<FilterExpression<T>> buildFilterExpressions(final Locale locale, final Http.Request httpRequest) {
        final RangeTermFacetedSearchSearchModel<T> searchModel = RangeTermFacetedSearchSearchModel.of(getLocalizedAttributePath(locale));
        return parseFilterRange(
                findSelectedValueFromQueryString(getLowerEndpointSettings(), httpRequest),
                findSelectedValueFromQueryString(getUpperEndpointSettings(), httpRequest))
                .map(searchModel::isBetween)
                .orElseGet(searchModel::allRanges).filterExpressions();
    }

    @Override
    default RangeFacetExpression<T> buildFacetExpression(final Locale locale) {
        return RangeTermFacetSearchModel.<T, String>of(getLocalizedAttributePath(locale), Function.identity())
                .withCountingProducts(true)
                .allRanges();
    }

    default Optional<SimpleRangeStats> findFacetResult(final PagedSearchResult<T> pagedSearchResult, final Locale locale) {
        final RangeFacetExpression<T> facetExpression = buildFacetExpression(locale);
        return Optional.ofNullable(pagedSearchResult.getRangeStatsOfAllRanges(facetExpression));
    }

    static <T> SliderRangeFacetedSearchFormSettings<T> of(final String fieldName, final String label, final String attributePath,
                                                          final boolean isCountDisplayed, @Nullable final String uiType,
                                                          final RangeEndpointFormSettings lowerEndpointSettings, final RangeEndpointFormSettings upperEndpointSettings) {
        return new SliderRangeFacetedSearchFormSettingsImpl<>(fieldName, label, attributePath, isCountDisplayed, uiType, lowerEndpointSettings, upperEndpointSettings);
    }
}
