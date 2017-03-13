package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.search.facetedsearch.mappers.FacetMapperSettings;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.model.FilterRange;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.findAllSelectedValuesFromQueryString;

public interface RangeFacetedSearchFormSettings extends FacetedSearchFormSettings {

    @Override
    default <T> RangeFacetedSearchExpression<T> buildSearchExpression(final Http.Request httpRequest, final Locale locale, final Class<T> resourceClass) {
        final RangeTermFacetedSearchSearchModel<T> searchModel = RangeTermFacetedSearchSearchModel.of(getLocalizedAttributePath(locale));
        final List<FilterRange<String>> selectedValues = convertToRanges(findAllSelectedValuesFromQueryString(getFieldName(), httpRequest));
        final RangeFacetedSearchExpression<T> facetedSearchExpr;
        if (selectedValues.isEmpty()) {
            facetedSearchExpr = searchModel.allRanges();
        } else {
            facetedSearchExpr = searchModel.isBetweenAny(selectedValues);
        }
        return facetedSearchExpr;
    }

    default List<FilterRange<String>> convertToRanges(List<String> selectedValues) {
        List<FilterRange<String>> result = new ArrayList<>();
        for (String value : selectedValues) {
            String[] tempArr = value.split(" to ");
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

    static RangeFacetedSearchFormSettings of(final String fieldName, final String label, final String expression, final int position,
                                             final FacetUIType facetType, final boolean isCountDisplayed, final boolean isMultiSelect,
                                             final boolean isMatchingAll, @Nullable final FacetMapperSettings mapper) {
        return new RangeFacetedSearchFormSettingsImpl(fieldName, label, expression, position, facetType, isCountDisplayed, isMultiSelect, isMatchingAll, mapper);
    }
}
