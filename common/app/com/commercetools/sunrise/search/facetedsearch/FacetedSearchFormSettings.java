package com.commercetools.sunrise.search.facetedsearch;

import com.commercetools.sunrise.framework.viewmodels.forms.WithFormFieldName;
import io.sphere.sdk.facets.FacetType;

import javax.annotation.Nullable;

public interface FacetedSearchFormSettings extends WithFormFieldName {

    /**
     * Gets the type of this facet.
     * @return the type of this facet
     */
    FacetType getType();

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
     * Whether the facet count should be hidden or not.
     * @return true if the count should be hidden, false otherwise
     */
    boolean isCountDisplayed();

    /**
     * Defines whether multiple options can be selected or only one.
     * @return true if multiple values can be selected, false otherwise
     */
    boolean isMultiSelect();

    /**
     * Defines whether the results should match all selected values in the facet (AND operator effect)
     * or just at least one selected value (OR operator effect)
     * @return true if results should match all selected values, false otherwise
     */
    boolean isMatchingAll();

    /**
     * Gets the mapper for this facet.
     * @return the facet option mapper, or absent if there is no mapper
     */
    @Nullable
    TermFacetResultMapper getMapper();

    static FacetedSearchFormSettings of(final String fieldName, final FacetType facetType, @Nullable final Long limit,
                                        @Nullable final Long threshold, final boolean isCountDisplayed,
                                        final boolean isMultiSelect, final boolean isMatchingAll,
                                        @Nullable final TermFacetResultMapper mapper) {
        return new FacetedSearchFormSettingsImpl(fieldName, facetType, limit, threshold, isCountDisplayed, isMultiSelect, isMatchingAll, mapper);
    }
}
