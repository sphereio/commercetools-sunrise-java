package com.commercetools.sunrise.ctp.project;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;

import java.time.Duration;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;

public final class ProjectModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    public Project provideProject(final SphereClient sphereClient) {
        final SphereRequest<Project> request = ProjectGet.of();
        return blockingWait(sphereClient.execute(request), Duration.ofSeconds(30));
    }
}
