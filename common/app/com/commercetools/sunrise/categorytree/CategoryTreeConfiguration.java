package com.commercetools.sunrise.categorytree;

import io.sphere.sdk.models.Base;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public final class CategoryTreeConfiguration extends Base {

    private final Integer cacheExpiration;
    private final String cacheKey;
    private final boolean discardEmpty;
    private List<String> sortExpressions;
    @Nullable
    private final String navigationExtId;
    @Nullable
    private final String newExtId;
    @Nullable
    private final String onSaleExtId;
    private final String onSaleExpression;

    @Inject
    CategoryTreeConfiguration(final Configuration configuration) {
        this.cacheKey = configuration.getString("categoryTree.cacheKey");
        this.cacheExpiration = configuration.getInt("categoryTree.cacheExpiration");
        this.discardEmpty = configuration.getBoolean("categoryTree.discardEmpty");
        this.sortExpressions = configuration.getStringList("categoryTree.sortExpressions");
        this.navigationExtId = configuration.getString("categoryTree.navigationExternalId");
        this.newExtId = configuration.getString("categoryTree.newExternalId");
        this.onSaleExtId = configuration.getString("categoryTree.onSaleExternalId");
        this.onSaleExpression = configuration.getString("categoryTree.onSaleExpression");
    }

    public String cacheKey() {
        return cacheKey;
    }

    public Optional<Integer> cacheExpiration() {
        return Optional.ofNullable(cacheExpiration);
    }

    public boolean discardEmpty() {
        return discardEmpty;
    }

    public List<String> sortExpressions() {
        return sortExpressions;
    }

    public Optional<String> navigationExternalId() {
        return Optional.ofNullable(navigationExtId);
    }

    public Optional<String> newExtId() {
        return Optional.ofNullable(newExtId);
    }

    public Optional<String> onSaleExtId() {
        return Optional.ofNullable(onSaleExtId);
    }

    public String onSaleExpression() {
        return onSaleExpression;
    }
}
