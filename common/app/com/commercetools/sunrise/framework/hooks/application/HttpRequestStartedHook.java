package com.commercetools.sunrise.framework.hooks.application;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctpevents.CtpEventHook;
import play.mvc.Http;

import java.util.concurrent.CompletionStage;

public interface HttpRequestStartedHook extends CtpEventHook {

    CompletionStage<?> onHttpRequestStarted(final Http.Context httpContext);

    static CompletionStage<?> runHook(final HookRunner hookRunner, final Http.Context httpContext) {
        return hookRunner.runEventHook(HttpRequestStartedHook.class, hook -> hook.onHttpRequestStarted(httpContext));
    }
}
