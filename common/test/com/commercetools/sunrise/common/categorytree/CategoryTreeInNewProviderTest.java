package com.commercetools.sunrise.common.categorytree;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import org.junit.Test;
import play.Configuration;

import java.util.Map;

import static com.commercetools.sunrise.common.utils.JsonUtils.readCtpObject;
import static com.google.inject.name.Names.named;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CategoryTreeInNewProviderTest {

    @Test
    public void get() {
        CategoryTree categoryTree = provideCategoryTree(singletonMap("common.newCategoryExternalId", 1));

        assertThat(categoryTree.getAllAsFlatList()).hasSize(3);
        assertThat(categoryTree.getRoots()).hasSize(0);
        assertThat(categoryTree.getSubtreeRoots()).hasSize(1);
        Category subtreeRoot = categoryTree.getSubtreeRoots().get(0);
        assertThat(subtreeRoot.getId()).isEqualTo("38429a3d-0cea-4a90-9e72-60ce3681e14e");
        assertThat(categoryTree.findChildren(subtreeRoot)).hasSize(2);
    }

    @Test
    public void get_whenConfigurationMissing() throws Exception {
        CategoryTree categoryTree = provideCategoryTree(emptyMap());

        assertThat(categoryTree.getAllAsFlatList()).isEmpty();
    }

    private CategoryTree provideCategoryTree(Map<String, Object> conf) {
        Injector injector = Guice.createInjector(binder -> {
            binder.bind(CategoryTree.class).toInstance(mainCategoryTree());
            binder.bind(CategoryTreeRefresher.class).toInstance(mock(CategoryTreeRefresher.class));
            binder.bind(Configuration.class).toInstance(new Configuration(conf));

            binder.bind(CategoryTree.class).annotatedWith(named("new")).toProvider(CategoryTreeInNewProvider.class);
        });
        return injector.getInstance(Key.get(CategoryTree.class, named("new")));
    }

    private CategoryTree mainCategoryTree() {
        return CategoryTree.of(readCtpObject("data/categoriesNew.json", CategoryQuery.resultTypeReference()).getResults());
    }
}