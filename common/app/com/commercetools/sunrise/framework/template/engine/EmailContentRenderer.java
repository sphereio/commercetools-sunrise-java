package com.commercetools.sunrise.framework.template.engine;

import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@linkplain Qualifier Qualifier} for the content renderer used for emails.
 */
@Named("emailContentRenderer")
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface EmailContentRenderer {
}
