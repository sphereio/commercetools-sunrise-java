package com.commercetools.sunrise.ctp.project;

import com.google.inject.AbstractModule;
import io.sphere.sdk.projects.Project;

import javax.inject.Singleton;

public final class ProjectModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Project.class).toProvider(ProjectProvider.class).in(Singleton.class);
    }
}
