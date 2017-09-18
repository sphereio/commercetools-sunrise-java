package com.commercetools.sunrise.ctp.client;

import com.commercetools.sunrise.play.configuration.SunriseConfigurationException;
import com.google.inject.Provider;
import io.sphere.sdk.client.SphereClientConfig;
import play.Configuration;

import javax.inject.Inject;

public final class SphereClientConfigProvider implements Provider<SphereClientConfig> {

    private final Configuration configuration;

    @Inject
    SphereClientConfigProvider(final Configuration globalConfig) {
        this.configuration = globalConfig.getConfig("sunrise.ctp.client");
    }

    @Override
    public SphereClientConfig get() {
        try {
            final String projectKey = configuration.getString("projectKey");
            final String clientId = configuration.getString("clientId");
            final String clientSecret = configuration.getString("clientSecret");
            return SphereClientConfig.of(projectKey, clientId, clientSecret)
                    .withApiUrl(configuration.getString("apiUrl"))
                    .withAuthUrl(configuration.getString("authUrl"));
        } catch (IllegalArgumentException e) {
            throw new SunriseConfigurationException("Could not initialize SphereClientConfig", "ctp.client", e);
        }
    }
}