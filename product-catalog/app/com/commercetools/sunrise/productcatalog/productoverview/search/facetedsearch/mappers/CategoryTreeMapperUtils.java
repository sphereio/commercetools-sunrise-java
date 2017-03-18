package com.commercetools.sunrise.productcatalog.productoverview.search.facetedsearch.mappers;

import play.mvc.Http;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

final class CategoryTreeMapperUtils {

    private CategoryTreeMapperUtils() {
    }

    public static Optional<String> findCategoryIdentifierInPath(final Http.Context httpContext) {
        return Optional.ofNullable(httpContext.args.get("ROUTE_PATTERN"))
                .map(routePattern -> routePattern.toString().replaceAll("<[^>]+>", "")) //hack since splitting '$categoryIdentifier<[^/]+>' with '/' would create more words
                .map(routePattern -> {
                    final List<String> paths = asList(routePattern.split("/"));
                    return paths.indexOf("$categoryIdentifier");
                })
                .filter(index -> index >= 0)
                .map(index -> httpContext.request().path().split("/")[index]);
    }
}
