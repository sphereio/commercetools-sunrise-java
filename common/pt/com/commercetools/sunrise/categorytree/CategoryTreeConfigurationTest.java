package com.commercetools.sunrise.categorytree;

import org.junit.Test;
import play.Application;
import play.Configuration;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTreeConfigurationTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure(Configuration.empty())
                .build();
    }

    @Test
    public void fallbacksToDefaultValues() throws Exception {
        final CategoryTreeConfiguration configuration = app.injector().instanceOf(CategoryTreeConfiguration.class);
        assertThat(configuration.cacheExpiration()).isEmpty();
        assertThat(configuration.cacheKey()).isNotNull();
        assertThat(configuration.discardEmpty()).isFalse();
        assertThat(configuration.sortExpressions()).isNotEmpty();
        assertThat(configuration.navigationExternalId()).isEmpty();
        assertThat(configuration.newExtId()).isEmpty();
    }
}