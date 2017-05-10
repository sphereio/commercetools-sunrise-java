package com.commercetools.sunrise.framework.hooks.ctpevents;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedResult;

import java.util.concurrent.CompletionStage;

public interface ProductProjectionPagedResultLoadedHook extends CtpEventHook {

    CompletionStage<?> onProductProjectionPagedResultLoaded(final PagedResult<ProductProjection> productProjectionPagedResult);

    static CompletionStage<?> runHook(final HookRunner hookRunner, final PagedResult<ProductProjection> productProjectionPagedResult) {
        return hookRunner.runEventHook(ProductProjectionPagedResultLoadedHook.class, hook -> hook.onProductProjectionPagedResultLoaded(productProjectionPagedResult));
    }
}
