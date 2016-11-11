package com.commercetools.sunrise.common.suggestion;

import com.commercetools.sunrise.common.contexts.UserContext;
import com.commercetools.sunrise.common.contexts.UserContextTestProvider;
import com.commercetools.sunrise.common.controllers.TestableSphereClient;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.CategoryOrderHints;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;
import org.junit.Test;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

public class SunriseProductRecommendationTest {

    @Test
    public void getsNoSuggestionsOnEmptyCategories() throws Exception {
        final Set<ProductProjection> recommendedProducts = productRecommendation(TestableSphereClient.ofEmptyResponse())
                .relatedToCategories(emptyList(), 5).toCompletableFuture().join();
        assertThat(recommendedProducts).isEmpty();
    }

    @Test
    public void getsNoSuggestionsOnEmptyProductCategories() throws Exception {
        final Set<ProductProjection> recommendedProducts = productRecommendation(TestableSphereClient.ofEmptyResponse())
                .relatedToProduct(productWithNoCategories(), 5).toCompletableFuture().join();
        assertThat(recommendedProducts).isEmpty();
    }

    private SunriseProductRecommendation productRecommendation(final SphereClient sphereClient) {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SphereClient.class).toInstance(sphereClient);
                bind(UserContext.class).toProvider(UserContextTestProvider.class);
            }
        });
        return injector.getInstance(SunriseProductRecommendation.class);
    }

    private ProductProjection productWithNoCategories() {
        return new ProductProjection() {
            @Override
            public Boolean hasStagedChanges() {
                return null;
            }

            @Override
            public Boolean isPublished() {
                return null;
            }

            @Override
            public Set<Reference<Category>> getCategories() {
                return emptySet();
            }

            @Nullable
            @Override
            public LocalizedString getDescription() {
                return null;
            }

            @Override
            public ProductVariant getMasterVariant() {
                return null;
            }

            @Nullable
            @Override
            public LocalizedString getMetaDescription() {
                return null;
            }

            @Nullable
            @Override
            public LocalizedString getMetaKeywords() {
                return null;
            }

            @Nullable
            @Override
            public LocalizedString getMetaTitle() {
                return null;
            }

            @Override
            public LocalizedString getName() {
                return null;
            }

            @Override
            public LocalizedString getSlug() {
                return null;
            }

            @Override
            public List<ProductVariant> getVariants() {
                return null;
            }

            @Override
            public SearchKeywords getSearchKeywords() {
                return null;
            }

            @Nullable
            @Override
            public CategoryOrderHints getCategoryOrderHints() {
                return null;
            }

            @Nullable
            @Override
            public ReviewRatingStatistics getReviewRatingStatistics() {
                return null;
            }

            @Nullable
            @Override
            public Reference<State> getState() {
                return null;
            }

            @Nullable
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public Reference<ProductType> getProductType() {
                return null;
            }

            @Nullable
            @Override
            public Reference<TaxCategory> getTaxCategory() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public Long getVersion() {
                return null;
            }

            @Override
            public ZonedDateTime getCreatedAt() {
                return null;
            }

            @Override
            public ZonedDateTime getLastModifiedAt() {
                return null;
            }
        };
    }

}
