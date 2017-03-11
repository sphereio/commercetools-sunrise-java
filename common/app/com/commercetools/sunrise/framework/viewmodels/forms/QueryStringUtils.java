package com.commercetools.sunrise.framework.viewmodels.forms;

import play.mvc.Http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.commercetools.sunrise.common.utils.ArrayUtils.arrayToList;
import static java.util.stream.Collectors.*;

public final class QueryStringUtils {

    private QueryStringUtils() {
    }

    /**
     * Finds all selected values for this field name in the HTTP request.
     * @param fieldName name of the form field
     * @param request current HTTP request
     * @return all the selected values for this field
     */
    public static List<String> findAllSelectedValuesFromQueryString(final String fieldName, final Http.Request request) {
        final String[] values = request.queryString().getOrDefault(fieldName, new String[]{});
        return arrayToList(values);
    }

    /**
     * Finds one selected value for this field name in the HTTP request.
     * @param fieldName name of the form field
     * @param request current HTTP request
     * @return a selected value for this field, or empty if none is selected
     */
    public static Optional<String> findSelectedValueFromQueryString(final String fieldName, final Http.Request request) {
        return Optional.ofNullable(request.getQueryString(fieldName));
    }

    /**
     * Finds all selected valid values for this form in the HTTP request.
     * @param settings the form settings
     * @param request current HTTP request
     * @param <T> type of the form options
     * @return a list of valid selected value for this form, or the default value if no valid value is selected
     */
    public static <T> List<T> findAllSelectedValuesFromQueryString(final FormSettings<T> settings, final Http.Request request) {
        final List<T> selectedValues = findAllSelectedValuesFromQueryString(settings.getFieldName(), request).stream()
                .map(settings::mapToValue)
                .filter(settings::isValidValue)
                .collect(toList());
        if (selectedValues.isEmpty()) {
            selectedValues.add(settings.getDefaultValue());
        }
        return selectedValues;
    }

    /**
     * Finds one selected valid value for this form in the HTTP request.
     * If many valid values are selected, which one is going to be returned is non-deterministic.
     * @param settings the form settings
     * @param request current HTTP request
     * @param <T> type of the form options
     * @return a valid selected value for this form, or the default value if no valid value is selected
     */
    public static <T> T findSelectedValueFromQueryString(final FormSettings<T> settings, final Http.Request request) {
        return findAllSelectedValuesFromQueryString(settings.getFieldName(), request).stream()
                .map(settings::mapToValue)
                .filter(settings::isValidValue)
                .findAny()
                .orElseGet(settings::getDefaultValue);
    }

    /**
     * Finds all selected valid options for this form in the HTTP request.
     * @param settings the form with options settings
     * @param request current HTTP request
     * @param <T> type of the form options
     * @return a list of valid selected options for this form
     */
    public static <T extends FormOption<V>, V> List<T> findAllSelectedValuesFromQueryString(final FormSettingsWithOptions<T, V> settings, final Http.Request request) {
        final List<String> selectedValues = findAllSelectedValuesFromQueryString(settings.getFieldName(), request);
        return settings.getOptions().stream()
                .filter(option -> selectedValues.contains(option.getFieldValue()))
                .collect(toList());
    }

    /**
     * Finds one selected valid option for this form in the HTTP request.
     * If many valid values are selected, which one is going to be returned is non-deterministic.
     * @param settings the form with options settings
     * @param request current HTTP request
     * @param <T> type of the form options
     * @return a valid selected option for this form, or empty if no valid option is selected
     */
    public static <T extends FormOption<V>, V> Optional<T> findSelectedValueFromQueryString(final FormSettingsWithOptions<T, V> settings, final Http.Request request) {
        final List<String> selectedValues = findAllSelectedValuesFromQueryString(settings.getFieldName(), request);
        return settings.getOptions().stream()
                .filter(option -> selectedValues.contains(option.getFieldValue()))
                .findAny()
                .map(Optional::of)
                .orElseGet(settings::findDefaultOption);
    }

    /**
     * Extracts the query string from the request.
     * @param request current HTTP request
     * @return query string from the request
     */
    public static Map<String, List<String>> extractQueryString(final Http.Request request) {
        return request.queryString().entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> arrayToList(entry.getValue())));
    }

    /**
     * Builds a URI from the given path and the query string, of the form: {@code path?foo=1&bar=2}
     * @param path URI path without query string
     * @param queryString the query string
     * @return a URI composed by the path and query string
     */
    public static String buildUri(final String path, final Map<String, List<String>> queryString) {
        final String queryStringAsString = buildQueryString(queryString);
        return path + (queryStringAsString.isEmpty() ? "" : "?" + queryStringAsString);
    }

    private static String buildQueryString(final Map<String, List<String>> queryString) {
        return queryString.entrySet().stream()
                .map(parameter -> buildQueryStringOfParameter(parameter.getKey(), parameter.getValue()))
                .filter(keyValue -> !keyValue.isEmpty())
                .collect(joining("&"));
    }

    private static String buildQueryStringOfParameter(final String key, final List<String> values) {
        if (values.isEmpty()) {
            return "";
        } else {
            return values.stream()
                    .map(QueryStringUtils::encode)
                    .collect(joining("&" + key + "=", key + "=", ""));
        }
    }

    /**
     * URL-encodes the given value via {@link URLEncoder#encode(String, String)}.
     * @param value the value to encode
     * @return the URL-encoded value
     */
    private static String encode(final String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Couldn't encode value", e);
        }
    }
}
