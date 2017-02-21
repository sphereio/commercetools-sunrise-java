package com.commercetools.sunrise.common.utils;

import play.mvc.Http;
import play.utils.UriEncoding;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.commercetools.sunrise.common.utils.ArrayUtils.arrayToList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public final class UrlUtils {

    private UrlUtils() {
    }

    /**
     * Builds an url encoded URI from the given path and the query string, of the form: path?foo=1&bar=2.
     *
     * @param path URI path without query string
     * @param queryString the query string
     *
     * @return a URI composed by the path and query string
     */
    public static String buildUrl(final String path, final Map<String, List<String>> queryString) {
        return buildUrl(path, buildQueryString(queryString));
    }

    /**
     * @param path the path
     * @param queryString the url encoded query string
     *
     * @return a URI composed by the path and query string
     */
    private static String buildUrl(final String path, final String queryString) {
        return path + (queryString.isEmpty() ? "" : "?" + queryString);
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
        }
        else {
            final String encodedKey = encode(key);
            return values.stream()
                    .map(UrlUtils::encode)
                    .collect(joining("&" + encodedKey + "=", encodedKey + "=", ""));
        }
    }

    /**
     * Url encodes the given string via {@link URLEncoder#encode(String, String)}.
     *
     * @param string the string to encode
     *
     * @return the url encoded string
     */
    private static String encode(String string) {
        try {
            final String encoded = URLEncoder.encode(string, StandardCharsets.UTF_8.name());
            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Couldn't encode string", e);
        }
    }

    public static Map<String, List<String>> getQueryString(final Http.Request request) {
        return request.queryString().entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> arrayToList(e.getValue())));
    }
}
