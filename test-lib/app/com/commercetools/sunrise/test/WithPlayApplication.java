package com.commercetools.sunrise.test;

import play.Application;
import play.test.WithApplication;

import static com.commercetools.sunrise.it.TestFixtures.provideSimpleApplicationBuilder;

public abstract class WithPlayApplication extends WithApplication {

    @Override
    protected Application provideApplication() {
        return provideSimpleApplicationBuilder().build();
    }
}