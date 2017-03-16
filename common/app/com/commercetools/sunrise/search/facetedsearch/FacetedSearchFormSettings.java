package com.commercetools.sunrise.search.facetedsearch;

import io.sphere.sdk.search.FacetExpression;
import io.sphere.sdk.search.FacetedSearchExpression;
import io.sphere.sdk.search.FilterExpression;
import play.mvc.Http;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.search.SearchUtils.localizeExpression;

public interface FacetedSearchFormSettings<T> {

    /**
     * Gets the label displayed in the facet.
     * @return the label displayed in this facet
     */
    String getLabel();

    /**
     * Gets the facet expression associated, representing just the attribute path.
     * The expression might contain {@code {{locale}}}, which should be replaced with the current locale before using it.
     * @return the facet expression
     */
    String getAttributePath();

    /**
     * Gets the localized expression associated, representing just the attribute path.
     * This expression is ready to be sent to the CTP platform.
     * @param locale the current user's locale
     * @return the localized facet expression
     */
    default String getLocalizedAttributePath(final Locale locale) {
        return localizeExpression(getAttributePath(), locale);
    }

    /**
     * Gets the position of this facet in relation to other facets.
     * @return a natural number indicating the position of this facet
     */
    int getPosition();

    /**
     * Whether the facet count should be hidden or not.
     * @return true if the count should be hidden, false otherwise
     */
    boolean isCountDisplayed();

    /**
     * Gets the UI type of this facet.
     * @return the UI type of this facet
     */
    @Nullable
    String getUIType();

    FacetedSearchExpression<T> buildFacetedSearchExpression(final Locale locale, final Http.Request httpRequest);

    List<FilterExpression<T>> buildFilterExpressions(final Locale locale, final Http.Request httpRequest);

    FacetExpression<T> buildFacetExpression(final Locale locale);
}
