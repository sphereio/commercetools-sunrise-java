package com.commercetools.sunrise.common.categorytree;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.categories.queries.CategoryQuery;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

import static com.commercetools.sunrise.common.utils.JsonUtils.readCtpObject;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RefreshableCategorySubtreeTest {

    @Test
    public void refresh_verifyNotifyingBehavior() {
        CategoryTreeRefresher refresher = mock(CategoryTreeRefresher.class);
        Supplier<CategoryTree> supplier = buildStrategy();
        RefreshableCategorySubtree categorySubtree = RefreshableCategorySubtree.of(supplier, refresher);
        RefreshableCategorySubtree subtree = spy(categorySubtree);
        doAnswer(i -> refresh(subtree)).when(refresher).refresh();

        refresher.refresh();

        verify(refresher).addListener(same(categorySubtree));
        verify(subtree).onRefresh();
        verify(supplier, times(2)).get();
        List<Category> allAsFlatList = subtree.getAllAsFlatList();
        assertThat(allAsFlatList).hasSize(4);
        assertThat(allAsFlatList.get(2).getId()).isEqualTo("6043642c-2a75-4be7-9817-a611aad5711a");
    }

    private Supplier<CategoryTree> buildStrategy() {
        CategoryTree suppliedOnConstruction = CategoryTree.of(emptyList());
        CategoryTree suppliedOnRefresh = CategoryTree.of(readCtpObject("data/categories.json", CategoryQuery.resultTypeReference()).getResults());
        @SuppressWarnings("unchecked")
        Supplier<CategoryTree> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn(suppliedOnConstruction, suppliedOnRefresh);
        return supplier;
    }

    private Object refresh(CategoryTreeRefreshListener subtree) {
        subtree.onRefresh();
        return null;
    }

}