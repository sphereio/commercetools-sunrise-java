package com.commercetools.sunrise.productcatalog.productoverview.search;

import com.commercetools.sunrise.framework.SunriseConfigurationException;
import com.commercetools.sunrise.search.sort.SortFormOption;
import com.commercetools.sunrise.search.sort.SortFormSettings;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.SortExpression;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static com.commercetools.sunrise.search.SearchUtils.localizeExpression;

@Singleton
public final class ProductSortFormSettings extends SortFormSettings<SortExpression<ProductProjection>> {

    private static final String CONFIG = "pop.sortProducts";

    @Inject
    ProductSortFormSettings(final Configuration configuration) {
        super(Optional.ofNullable(configuration.getConfig(CONFIG))
                .orElseThrow(() -> new SunriseConfigurationException("Could not find sort configuration", CONFIG)),
                SortExpression::of,
                (expr, locale) -> SortExpression.of(localizeExpression(expr.expression(), locale)));
    }

    @Override
    public String getFieldName() {
        return super.getFieldName();
    }

    @Override
    public List<SortFormOption<SortExpression<ProductProjection>>> getOptions() {
        return super.getOptions();
    }

    @Override
    public Optional<SortFormOption<SortExpression<ProductProjection>>> findDefaultOption() {
        return super.findDefaultOption();
    }
}
