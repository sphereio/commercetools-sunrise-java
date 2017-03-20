package com.commercetools.sunrise;

import com.commercetools.sunrise.it.WithSphereClient;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleControllerIntegrationTest extends WithSphereClient {

    @Test
    public void itFindsSomeCategories() throws Exception {
        final PagedQueryResult<Category> response = sphereClient.executeBlocking(CategoryQuery.of());
        assertThat(response.getResults()).isNotEmpty();
    }
}
