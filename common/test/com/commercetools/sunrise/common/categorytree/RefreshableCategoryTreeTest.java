package com.commercetools.sunrise.common.categorytree;

import com.commercetools.sunrise.common.controllers.TestableSphereClient;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import java.util.List;

import static com.commercetools.sunrise.common.utils.JsonUtils.readCtpObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RefreshableCategoryTreeTest {

    @Test
    public void refresh_verifyNotifyingBehavior() {
        CategoryTreeRefresher refresher = RefreshableCategoryTree.of(TestableSphereClient.ofResponse(PagedQueryResult.empty()));
        CategoryTreeRefreshListener listener1 = spy(CategoryTreeRefreshListener.class);
        CategoryTreeRefreshListener listener2 = spy(CategoryTreeRefreshListener.class);
        refresher.addListener(listener1);
        refresher.addListener(listener2);

        refresher.refresh();

        verify(listener1).onRefresh();
        verify(listener2).onRefresh();
    }

    @Test
    public void refresh_verifyCategoryTreeIsLoaded_andSorted() {
        final PagedQueryResult<Category> categories = readCtpObject("data/categories.json", CategoryQuery.resultTypeReference());
        CategoryTree categoryTree = RefreshableCategoryTree.of(TestableSphereClient.ofResponse(categories));

        ((CategoryTreeRefresher) categoryTree).refresh();

        List<Category> allAsFlatList = categoryTree.getAllAsFlatList();
        assertThat(allAsFlatList).hasSize(4);
        assertThat(allAsFlatList.get(2).getId()).isEqualTo("7e06b4a0-d9d9-436e-beb6-3cdaaeccdef8");
        assertThat(allAsFlatList.get(3).getId()).isEqualTo("6043642c-2a75-4be7-9817-a611aad5711a");
    }

}