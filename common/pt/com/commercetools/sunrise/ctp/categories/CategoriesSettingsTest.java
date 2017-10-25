package com.commercetools.sunrise.ctp.categories;

import com.commercetools.sunrise.test.WithPlayApplication;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesSettingsTest extends WithPlayApplication {

    @Test
    public void fallbacksToDefaultValues() throws Exception {
        final CategoriesSettings categoriesSettings = app.injector().instanceOf(CategoriesSettings.class);
        assertThat(categoriesSettings.cacheExpiration())
                .as("Cache expiration")
                .isEmpty();
        assertThat(categoriesSettings.cacheKey())
                .as("Cache key")
                .isNotNull();
        assertThat(categoriesSettings.discardEmpty())
                .as("Discard empty")
                .isFalse();
        assertThat(categoriesSettings.sortExpressions())
                .as("Sort expressions")
                .isNotEmpty();
        assertThat(categoriesSettings.navigationExternalId())
                .as("Navigation external ID")
                .isEmpty();
        assertThat(categoriesSettings.newExtId())
                .as("New external ID")
                .isEmpty();
        assertThat(categoriesSettings.specialCategories())
                .as("Special categories")
                .isEmpty();
    }
}