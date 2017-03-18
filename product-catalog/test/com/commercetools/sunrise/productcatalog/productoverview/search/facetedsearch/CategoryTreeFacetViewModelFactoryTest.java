package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch;

import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.framework.viewmodels.forms.FormSelectableOptionViewModel;
import com.commercetools.sunrise.productcatalog.productoverview.CategoryFinder;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.CategoryTreeFacetViewModelFactory;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.DefaultCategoryTreeFacetedSearchFormSettings;
import com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree.ProductTermFacetMapperType;
import com.commercetools.sunrise.search.facetedsearch.terms.SimpleTermFacetedSearchFormSettings;
import com.commercetools.sunrise.search.facetedsearch.terms.mappers.TermFacetMapperSettings;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import com.commercetools.sunrise.test.TestableCall;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.search.TermFacetResult;
import io.sphere.sdk.search.TermStats;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryTreeFacetViewModelFactoryTest {

    private final static String CAT_A_ID = "d5a0952b-6574-49c9-b0cd-61e0d21d36cc";
    private final static String CAT_B_ID = "e92b6d26-7a34-4960-804c-0fc9e40c64e3";
    private final static String CAT_C_ID = "1acce167-cd23-4fd7-b344-af76941cb375";
    private final static String CAT_D_ID = "f5b288e8-d19c-4c0d-a18a-8ca68f982b8e";
    private final static String CAT_E_ID = "b00b1eb9-f051-4f13-8f9c-1bb73e13e8a1";
    private static final Category CAT_A = category(CAT_A_ID, null, "A");
    private static final Category CAT_B = category(CAT_B_ID, CAT_A_ID, "B");
    private static final Category CAT_C = category(CAT_C_ID, CAT_B_ID, "C");
    private static final Category CAT_D = category(CAT_D_ID, CAT_B_ID, "D");
    private static final Category CAT_E = category(CAT_E_ID, CAT_A_ID, "E");
    private static final TermStats TERM_B = TermStats.of(CAT_B_ID, 1L);
    private static final TermStats TERM_C = TermStats.of(CAT_C_ID, 2L);
    private static final TermStats TERM_D = TermStats.of(CAT_D_ID, 3L);
    private static final List<Category> SELECTED_CATEGORIES = singletonList(CAT_B);

    /* A
     * |-- B
     * |   |-- C
     * |   |-- D
     * |
     * |-- E
     */

    @Test
    public void resolvesCategory() throws Exception {
        test(singletonList(CAT_B), singletonList(TERM_B), viewModels -> {
            assertThat(viewModels).hasSize(1);
            final FacetOptionViewModel viewModel = viewModels.get(0);
            assertThat(viewModel.getLabel()).isEqualTo("B");
            assertThat(viewModel.getValue()).isEqualTo("B-slug");
            assertThat(viewModel.getCount()).isEqualTo(1);
            assertThat(viewModel.getChildren()).isEmpty();
        });
    }

    @Test
    public void emptyWithEmptyCategoryTree() throws Exception {
        test(emptyList(), singletonList(TERM_D), viewModels -> assertThat(viewModels).isEmpty());
    }

    @Test
    public void emptyWithEmptyFacetResults() throws Exception {
        test(singletonList(CAT_D), emptyList(), viewModels -> assertThat(viewModels).isEmpty());
    }

    @Test
    public void keepsOrderFromCategoryTree() throws Exception {
        test(asList(CAT_C, CAT_D), asList(TERM_D, TERM_C), viewModels ->
                assertThat(viewModels)
                        .extracting(FormSelectableOptionViewModel::getLabel)
                        .containsExactly("C", "D"));
    }

    @Test
    public void inheritsInformationFromLeaves() throws Exception {
        test(asList(CAT_B, CAT_C, CAT_D), asList(TERM_D, TERM_C, TERM_B), viewModels -> {
            assertThat(viewModels).hasSize(1);
            final FacetOptionViewModel viewModel = viewModels.get(0);
            assertThat(viewModel.getLabel()).isEqualTo("B");
            assertThat(viewModel.getCount()).isEqualTo(6);
            assertThat(viewModel.isSelected()).isTrue();
            assertThat(viewModel.getChildren())
                    .extracting(FormSelectableOptionViewModel::getLabel)
                    .containsExactly("C", "D");
        });
    }

    @Test
    public void discardsEmptyBranches() throws Exception {
        test(asList(CAT_A, CAT_B, CAT_C, CAT_D, CAT_E), asList(TERM_D, TERM_C, TERM_B), viewModels -> {
            assertThat(viewModels).hasSize(1);
            final FacetOptionViewModel viewModelA = viewModels.get(0);
            assertThat(viewModelA.getLabel()).isEqualTo("A");
            assertThat(viewModelA.getCount()).isEqualTo(6);
            assertThat(viewModelA.isSelected()).isFalse();
            assertThat(viewModelA.getChildren()).hasSize(1);

            final FacetOptionViewModel viewModelB = viewModelA.getChildren().get(0);
            assertThat(viewModelB.getLabel()).isEqualTo("B");
            assertThat(viewModelB.getCount()).isEqualTo(6);
            assertThat(viewModelB.isSelected()).isTrue();
            assertThat(viewModelB.getChildren())
                    .extracting(FormSelectableOptionViewModel::getLabel)
                    .containsExactly("C", "D");
        });
    }

    private void test(final List<Category> categories, final List<TermStats> termStats, final Consumer<List<FacetOptionViewModel>> test) {
        final TermFacetMapperSettings mapperSettings = TermFacetMapperSettings.of(ProductTermFacetMapperType.CATEGORY_TREE, null);
        final SimpleTermFacetedSearchFormSettings<Object> bar = SimpleTermFacetedSearchFormSettings.of("bar", "", "", false, null, false, false, mapperSettings, null, null);
        final UserLanguage userLanguage = new UserLanguage() {
            @Override
            public Locale locale() {
                return Locale.ENGLISH;
            }

            @Override
            public List<Locale> locales() {
                return singletonList(Locale.ENGLISH);
            }
        };
        final CategoryTree categoryTree = CategoryTree.of(categories);
        final CategoryFinder categoryFinder = identifier -> completedFuture(categoryTree.findById(identifier));
        final DefaultCategoryTreeFacetedSearchFormSettings settings = new DefaultCategoryTreeFacetedSearchFormSettings(bar, Locale.ENGLISH, categoryFinder);
        final CategoryTreeFacetViewModelFactory mapper = new CategoryTreeFacetViewModelFactory(userLanguage, categoryTree, categoryFinder, reverseRouter());
        final TermFacetResult termFacetResult = TermFacetResult.of(0L, 0L, 0L, termStats);
        final List<FacetOptionViewModel> viewModels = mapper.apply(termFacetResult, emptyList());
        test.accept(viewModels);
    }

    private static Category category(final String id, @Nullable final String parentId, final String name) {
        final Category category = mock(Category.class);
        when(category.getId()).thenReturn(id);
        when(category.getName().find(Locale.ENGLISH)).thenReturn(Optional.of(name));
        when(category.getSlug().find(Locale.ENGLISH)).thenReturn(Optional.of(name + "-slug"));
        when(category.getParent()).thenReturn(parentId == null ? null : Category.reference(parentId));
        return category;
    }

    private static ProductReverseRouter reverseRouter() {
        final ProductReverseRouter productReverseRouter = mock(ProductReverseRouter.class);
        when(productReverseRouter.productOverviewPageCall(any(Category.class)))
                .then(invocation -> ((Category) invocation.getArgument(0)).getSlug()
                        .find(Locale.ENGLISH)
                        .map(TestableCall::new));
        return productReverseRouter;
    }
}
