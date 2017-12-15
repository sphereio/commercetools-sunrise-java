package com.commercetools.sunrise.models.project;

import com.google.inject.AbstractModule;
import io.sphere.sdk.projects.Project;

import javax.inject.Singleton;

/**
 * Module that allows to inject a commercetools {@link Project}.
 */
public final class ProjectModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Project.class)
                .toProvider(CachedProjectProvider.class)
                .in(Singleton.class);
    }
}
