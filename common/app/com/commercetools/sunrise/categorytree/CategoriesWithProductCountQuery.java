package com.commercetools.sunrise.categorytree;

import com.commercetools.sunrise.ctp.graphql.GraphQLRequestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import play.libs.Json;

import static com.commercetools.sunrise.ctp.graphql.GraphQLUtils.readFromResource;

public final class CategoriesWithProductCountQuery extends GraphQLRequestBase<CategoriesWithProductCountQueryResult> {

    public CategoriesWithProductCountQuery(final int limit) {
        super("categories", resultTypeReference(), readFromResource("CategoriesWithProductCount.graphql"), Json.newObject().put("limit", limit));
    }

    public static TypeReference<CategoriesWithProductCountQueryResult> resultTypeReference() {
        return new TypeReference<CategoriesWithProductCountQueryResult>() {
            @Override
            public String toString() {
                return "TypeReference<CategoriesWithProductCountQueryResult>";
            }
        };
    }
}
