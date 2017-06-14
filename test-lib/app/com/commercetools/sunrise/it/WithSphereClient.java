package com.commercetools.sunrise.it;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.time.Duration;

public abstract class WithSphereClient extends WithApplication {

    protected volatile static BlockingSphereClient sphereClient;
    protected volatile static HttpClient httpClient;

    @BeforeClass
    public synchronized static void startSphereClient() {
        if (sphereClient == null) {
            httpClient = provideHttpClient();
            sphereClient = provideSphereClient(httpClient);
        }
    }

    @AfterClass
    public synchronized static void stopSphereClient() {
        if (sphereClient != null) {
            sphereClient.close();
            sphereClient = null;
            httpClient.close();
            httpClient = null;
        }
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(SphereClient.class).toInstance(sphereClient);
                    }
                }).build();
    }

    public static SphereClientConfig sphereClientConfig() {
        final Config configuration = ConfigFactory.load("it.conf");
        final String projectKey = configuration.getString("ctp.it.projectKey");
        final String clientId = configuration.getString("ctp.it.clientId");
        final String clientSecret = configuration.getString("ctp.it.clientSecret");
        return SphereClientConfig.of(projectKey, clientId, clientSecret);
    }

    public static BlockingSphereClient provideSphereClient() {
        return provideSphereClient(provideHttpClient());
    }

    public static BlockingSphereClient provideSphereClient(final HttpClient httpClient) {
        final SphereClientConfig config = sphereClientConfig();
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false);
        final SphereClient underlying = SphereClient.of(config, httpClient, tokenSupplier);
        return BlockingSphereClient.of(underlying, Duration.ofSeconds(30));
    }

    public static HttpClient provideHttpClient() {
        final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setAcceptAnyCertificate(true).build());
        return AsyncHttpClientAdapter.of(asyncHttpClient);
    }
}