package com.commercetools.sunrise.categorytree;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import java.util.List;

public final class CategoriesWithProductCountQueryResult extends Base {

    private final int total;
    private final List<CategoryWithProductCount> results;

    @JsonCreator
    public CategoriesWithProductCountQueryResult(@JsonProperty("total") final int total, @JsonProperty("results") final List<CategoryWithProductCount> results) {
        this.total = total;
        this.results = results;
    }

    public int getTotal() {
        return total;
    }

    public List<CategoryWithProductCount> getResults() {
        return results;
    }
}
