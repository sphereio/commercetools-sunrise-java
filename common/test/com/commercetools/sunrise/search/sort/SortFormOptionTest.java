package com.commercetools.sunrise.search.sort;

import org.junit.Test;

import java.util.Locale;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class SortFormOptionTest {

    @Test
    public void localizesSortExpressions() throws Exception {
        final String expr1 = "{{locale}}.foo.{{locale}} asc";
        final String expr2 = "foo.bar {{locale}} desc";
        final String expr3 = "foo.bar";
        final SortFormOption formOption = sortOption(expr1, expr2, expr3);
        assertThat(formOption.getValue())
                .containsExactly(expr1, expr2, expr3);
        assertThat(formOption.getLocalizedValue(Locale.ENGLISH))
                .containsExactly("en.foo.en asc", "foo.bar en desc", "foo.bar");
    }

    private static SortFormOption sortOption(final String ... expressions) {
        return SortFormOption.of("label", "value", asList(expressions), false);
    }
}
