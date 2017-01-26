package com.commercetools.sunrise.common;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class SunriseSolutionInfoTest {

    //need to create fakeHttpClient
    @Test
    public void userAgent() throws Exception {
        try (final FakeHttpClient httpClient = new FakeHttpClient()) {
            try (final SphereClient client = SphereClientFactory.of(() -> httpClient).createClient(getSphereClientConfig())) {
                client.execute(ProjectGet.of()).toCompletableFuture().join();
                assertThat(httpClient.getUserAgent())
                        .startsWith("commercetools-jvm-sdk")
                        .contains("JVM-SDK-integration-tests")
                        .contains(BuildInfo.version());
            }
        }
    }
}
