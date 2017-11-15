package com.commercetools.sunrise.framework.cms;

import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.framework.cms.filebased.FileBasedCmsService;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public final class CmsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CmsService.class)
                .to(FileBasedCmsService.class)
                .in(Singleton.class);
    }
}
