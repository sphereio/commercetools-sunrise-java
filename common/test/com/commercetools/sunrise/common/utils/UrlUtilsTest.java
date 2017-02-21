package com.commercetools.sunrise.common.utils;

import org.junit.Test;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static com.commercetools.sunrise.common.utils.UrlUtils.*;

/**
 * Unit tests for {@link UrlUtils}
 */
public class UrlUtilsTest {

    @Test
    public void buildsUrl() throws Exception {
        final Map<String, List<String>> queryString = new HashMap<>();

        queryString.put("foo", asList("jacket", "shirt", "shoes"));
        queryString.put("bar", asList("1", "2"));
        queryString.put("qux", singletonList("x"));
        queryString.put("baz", emptyList());

        final String url = buildUrl("some-path", queryString);
        final String expectedUrl = "some-path?bar=1&bar=2&qux=x&foo=jacket&foo=shirt&foo=shoes";

        assertThat(url).isEqualTo(expectedUrl);


        final String decodedUrl = URLDecoder.decode(url, "UTF-8");

        assertThat(decodedUrl).isEqualTo(expectedUrl);
    }

    @Test
    public void buildsUrl_Encoded() throws Exception {
        final Map<String, List<String>> queryString = new HashMap<>();

        queryString.put("foo", asList("jacket shirt", "shoes "));
        queryString.put("bar", asList("1 2"));

        final String url = buildUrl("some-path", queryString);
        assertThat(url).isEqualTo("some-path?bar=1+2&foo=jacket+shirt&foo=shoes+");

        final String decodedUrl = URLDecoder.decode(url, "UTF-8");

        assertThat(decodedUrl).isEqualTo("some-path?bar=1 2&foo=jacket shirt&foo=shoes ");
    }
}
