package com.commercetools.sunrise.productcatalog.productdetail;

import com.commercetools.sunrise.core.controllers.AbstractSphereRequestExecutor;
import com.commercetools.sunrise.core.hooks.HookRunner;
import com.commercetools.sunrise.core.hooks.ctpevents.ProductProjectionLoadedHook;
import com.commercetools.sunrise.core.hooks.ctprequests.ProductProjectionSearchHook;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.search.PagedSearchResult;
import play.libs.concurrent.HttpExecution;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public abstract class AbstractSingleProductSearchExecutor extends AbstractSphereRequestExecutor {

    protected AbstractSingleProductSearchExecutor(final SphereClient sphereClient, final HookRunner hookRunner) {
        super(sphereClient, hookRunner);
    }

    protected final CompletionStage<Optional<ProductProjection>> executeRequest(final ProductProjectionSearch baseRequest) {
        return executeRequest(baseRequest, PagedSearchResult::head);
    }

    protected final CompletionStage<Optional<ProductProjection>> executeRequest(final ProductProjectionSearch baseRequest,
                                                                                final Function<PagedSearchResult<ProductProjection>, Optional<ProductProjection>> productSelector) {
        final ProductProjectionSearch request = ProductProjectionSearchHook.runHook(getHookRunner(), baseRequest);
        return getSphereClient().execute(request)
                .thenApply(productSelector)
                .thenApplyAsync(productOpt -> {
                    productOpt.ifPresent(product -> ProductProjectionLoadedHook.runHook(getHookRunner(), product));
                    return productOpt;
                }, HttpExecution.defaultContext());
    }
}
