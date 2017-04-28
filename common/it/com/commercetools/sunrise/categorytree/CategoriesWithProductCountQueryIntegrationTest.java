package com.commercetools.sunrise.categorytree;

import com.commercetools.sunrise.it.WithSphereClient;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import static com.commercetools.sunrise.it.CategoryTestFixtures.categoryDraft;
import static com.commercetools.sunrise.it.CategoryTestFixtures.withCategory;
import static com.commercetools.sunrise.it.ProductTestFixtures.*;
import static com.commercetools.sunrise.it.ProductTypeTestFixtures.productTypeDraft;
import static com.commercetools.sunrise.it.ProductTypeTestFixtures.withProductType;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesWithProductCountQueryIntegrationTest extends WithSphereClient {

    @Test
    public void isExecuted() throws Exception {
        withProductAssignedToOneCategory(() -> {
            final CategoriesWithProductCountQuery query = new CategoriesWithProductCountQuery(20L, 0L);
            final PagedQueryResult<CategoryWithProductCount> result = sphereClient.executeBlocking(query);
            assertThat(result.getOffset()).as("offset").isEqualTo(0);
            assertThat(result.getCount()).as("count").isGreaterThanOrEqualTo(2);
            assertThat(result.getTotal()).as("total").isGreaterThanOrEqualTo(2);
            assertThat(result.getResults())
                    .as("results")
                    .extracting(CategoryWithProductCount::hasProducts)
                    .contains(true, false);
        });
    }

    private void withProductAssignedToOneCategory(final Runnable test) {
        withCategory(sphereClient, categoryDraft(), category1 -> {
            withCategory(sphereClient, categoryDraft(), category2 -> {
                withProductType(sphereClient, productTypeDraft(), productType -> {
                    final ProductDraft productDraft = productDraft(productType, productVariantDraft())
                            .categories(singletonList(category1.toReference()))
                            .build();
                    withProduct(sphereClient, productDraft, product -> {
                        test.run();
                        return product;
                    });
                    return productType;
                });
                return category2;
            });
            return category1;
        });
    }
}
