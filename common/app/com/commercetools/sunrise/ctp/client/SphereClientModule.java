package com.commercetools.sunrise.ctp.client;

import com.commercetools.sunrise.framework.controllers.metrics.SimpleMetricsSphereClientProvider;
import com.google.inject.AbstractModule;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;

import javax.inject.Singleton;

public final class SphereClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SphereClientConfig.class)
                .toProvider(SphereClientConfigProvider.class)
                .in(Singleton.class);
        bind(SphereClient.class)
                .toProvider(SimpleMetricsSphereClientProvider.class)
                .in(Singleton.class);
    }
}
