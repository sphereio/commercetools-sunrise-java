package com.commercetools.sunrise.it;

import com.google.inject.AbstractModule;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.http.HttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import static com.commercetools.sunrise.it.SphereClientFixtures.provideHttpClient;
import static com.commercetools.sunrise.it.SphereClientFixtures.provideSphereClient;

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
}