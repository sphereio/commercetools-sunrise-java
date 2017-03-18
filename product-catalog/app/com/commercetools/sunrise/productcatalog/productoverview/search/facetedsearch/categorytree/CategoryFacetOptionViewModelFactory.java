package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.categorytree;

import com.commercetools.sunrise.framework.injection.RequestScoped;
import com.commercetools.sunrise.framework.localization.UserLanguage;
import com.commercetools.sunrise.framework.reverserouters.productcatalog.product.ProductReverseRouter;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.AbstractFacetOptionViewModelFactory;
import com.commercetools.sunrise.search.facetedsearch.viewmodels.FacetOptionViewModel;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.search.TermStats;
import play.mvc.Http;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.buildUri;
import static com.commercetools.sunrise.framework.viewmodels.forms.QueryStringUtils.extractQueryString;

@RequestScoped
public class CategoryFacetOptionViewModelFactory extends AbstractFacetOptionViewModelFactory<TermStats, Category, String> {

    private final List<Locale> locales;
    private final Http.Request httpRequest;
    private final ProductReverseRouter productReverseRouter;

    @Inject
    public CategoryFacetOptionViewModelFactory(final UserLanguage userLanguage, final Http.Request httpRequest,
                                               final ProductReverseRouter productReverseRouter) {
        this.locales = userLanguage.locales();
        this.httpRequest = httpRequest;
        this.productReverseRouter = productReverseRouter;
    }

    @Override
    public final FacetOptionViewModel create(@Nullable final TermStats stats, final Category value, @Nullable final String selectedValue) {
        return super.create(stats, value, selectedValue);
    }

    @Override
    protected final void initialize(final FacetOptionViewModel viewModel, @Nullable final TermStats stats, final Category value, @Nullable final String selectedValue) {
        super.initialize(viewModel, stats, value, selectedValue);
    }

    @Override
    protected void fillLabel(final FacetOptionViewModel viewModel, @Nullable final TermStats stats, final Category category, @Nullable final String selectedValue) {
        viewModel.setLabel(category.getName().find(locales).orElseGet(category::getId));
    }

    @Override
    protected void fillValue(final FacetOptionViewModel viewModel, @Nullable final TermStats stats, final Category category, @Nullable final String selectedValue) {
        productReverseRouter.productOverviewPageCall(category).ifPresent(call -> {
            viewModel.setValue(buildUri(call.url(), extractQueryString(httpRequest)));
        });
    }

    @Override
    protected void fillSelected(final FacetOptionViewModel viewModel, @Nullable final TermStats stats, final Category category, @Nullable final String selectedValue) {
        viewModel.setSelected(category.getId().equals(selectedValue));
    }

    @Override
    protected void fillCount(final FacetOptionViewModel viewModel, @Nullable final TermStats stats, final Category category, @Nullable final String selectedValue) {
        Long count = 0L;
        if (stats != null && stats.getProductCount() != null) {
            count = stats.getProductCount();
        }
        viewModel.setCount(count);
    }
}
