package com.commercetools.sunrise.framework.hooks.application;

import com.commercetools.sunrise.framework.hooks.HookRunner;
import com.commercetools.sunrise.framework.hooks.ctpevents.CtpEventHook;
import play.mvc.Result;

public interface HttpRequestEndedHook extends CtpEventHook {

    Result onHttpRequestEnded(final Result result);

    static Result runHook(final HookRunner hookRunner, final Result result) {
        return hookRunner.runUnaryOperatorHook(HttpRequestEndedHook.class, HttpRequestEndedHook::onHttpRequestEnded, result);
    }
}
