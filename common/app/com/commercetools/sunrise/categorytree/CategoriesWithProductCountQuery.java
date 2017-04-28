package com.commercetools.sunrise.categorytree;

import com.commercetools.sunrise.ctp.graphql.GraphQLRequestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import play.libs.Json;

import javax.annotation.Nullable;
import java.util.List;

import static com.commercetools.sunrise.ctp.graphql.GraphQLUtils.readFromResource;
import static java.util.Collections.emptyList;

public final class CategoriesWithProductCountQuery extends GraphQLRequestBase<PagedQueryResult<CategoryWithProductCount>> implements QueryDsl<CategoryWithProductCount, CategoriesWithProductCountQuery> {

    private final Long limit;
    private final Long offset;

    public CategoriesWithProductCountQuery(final Long limit, final Long offset) {
        super("categories", resultTypeReference(), readFromResource("CategoriesWithProductCount.graphql"), Json.newObject().put("limit", limit));
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public CategoriesWithProductCountQuery withPredicates(final List<QueryPredicate<CategoryWithProductCount>> queryPredicates) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withPredicates(final QueryPredicate<CategoryWithProductCount> predicate) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusPredicates(final List<QueryPredicate<CategoryWithProductCount>> queryPredicates) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusPredicates(final QueryPredicate<CategoryWithProductCount> predicate) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withSort(final List<QuerySort<CategoryWithProductCount>> sort) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withSort(final QuerySort<CategoryWithProductCount> sort) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusSort(final List<QuerySort<CategoryWithProductCount>> sort) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusSort(final QuerySort<CategoryWithProductCount> sort) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withExpansionPaths(final List<ExpansionPath<CategoryWithProductCount>> expansionPaths) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withExpansionPaths(final ExpansionPath<CategoryWithProductCount> expansionPath) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusExpansionPaths(final List<ExpansionPath<CategoryWithProductCount>> expansionPaths) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery plusExpansionPaths(final ExpansionPath<CategoryWithProductCount> expansionPath) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withFetchTotal(final boolean fetchTotal) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withLimit(final Long limit) {
        return this;
    }

    @Override
    public CategoriesWithProductCountQuery withOffset(final Long offset) {
        return this;
    }

    @Override
    public List<QueryPredicate<CategoryWithProductCount>> predicates() {
        return emptyList();
    }

    @Override
    public List<QuerySort<CategoryWithProductCount>> sort() {
        return emptyList();
    }

    @Override
    public List<ExpansionPath<CategoryWithProductCount>> expansionPaths() {
        return emptyList();
    }

    @Nullable
    @Override
    public Boolean fetchTotal() {
        return true;
    }

    @Nullable
    @Override
    public Long limit() {
        return limit;
    }

    @Nullable
    @Override
    public Long offset() {
        return offset;
    }

    @Override
    public String endpoint() {
        return "graphql";
    }

    public static TypeReference<PagedQueryResult<CategoryWithProductCount>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<CategoryWithProductCount>>() {
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<CategoryWithProductCount>>";
            }
        };
    }
}
