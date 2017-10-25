package com.commercetools.sunrise.framework.injection;

import com.commercetools.sunrise.test.WithPlayApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.junit.Test;
import play.Application;

import java.util.concurrent.atomic.AtomicInteger;

import static com.commercetools.sunrise.it.TestFixtures.provideSimpleApplicationBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.invokeWithContext;

public class RequestScopedTest extends WithPlayApplication {

    @Override
    protected Application provideApplication() {
        final Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bindScope(RequestScoped.class, new RequestScope());
            }
        };
        return provideSimpleApplicationBuilder()
                .overrides(module)
                .build();
    }

    @Test
    public void keepsAliveInTheSameRequest() throws Exception {
        invokeWithContext(fakeRequest(), () -> {
            final RequestScopedClass instance1 = app.injector().instanceOf(RequestScopedClass.class);
            final RequestScopedClass instance2 = app.injector().instanceOf(RequestScopedClass.class);
            assertThat(instance1.getInstanceId()).isEqualTo(instance2.getInstanceId());
            assertThat(instance1)
                    .as("Request scoped instance is kept alive in the same request")
                    .isSameAs(instance2);
            return null;
        });
    }

    @Test
    public void createsANewInstanceBetweenRequests() throws Exception {
        final RequestScopedClass instance1 = invokeWithContext(fakeRequest(), () ->
                app.injector().instanceOf(RequestScopedClass.class));
        final RequestScopedClass instance2 = invokeWithContext(fakeRequest(), () ->
                app.injector().instanceOf(RequestScopedClass.class));
        assertThat(instance1.getInstanceId()).isNotEqualTo(instance2.getInstanceId());
        assertThat(instance1)
                .as("Request scoped instance is new with every request")
                .isNotSameAs(instance2);
    }

    @Test
    public void createsNewInstanceWhenNoHttpContextAvailable() throws Exception {
        final RequestScopedClass instance1 = app.injector().instanceOf(RequestScopedClass.class);
        final RequestScopedClass instance2 = app.injector().instanceOf(RequestScopedClass.class);
        assertThat(instance1.getInstanceId()).isNotEqualTo(instance2.getInstanceId());
        assertThat(instance1)
                .as("New request scoped instances are created when no HTTP context available")
                .isNotSameAs(instance2);
    }

    @RequestScoped
    private static class RequestScopedClass {

        private static final AtomicInteger instanceIdGenerator = new AtomicInteger(0);
        private final int id = instanceIdGenerator.incrementAndGet();

        int getInstanceId() {
            return id;
        }
    }
}
