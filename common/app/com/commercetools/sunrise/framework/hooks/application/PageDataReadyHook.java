package com.commercetools.sunrise.framework.hooks.application;

import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.commercetools.sunrise.framework.hooks.HookRunner;

public interface PageDataReadyHook extends ApplicationHook {

    void onPageDataReady(final PageData pageData);

    static void runHook(final HookRunner hookRunner, final PageData pageData) {
        hookRunner.runConsumerHook(PageDataReadyHook.class, hook -> hook.onPageDataReady(pageData));
    }
}
