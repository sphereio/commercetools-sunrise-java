package com.commercetools.sunrise.myaccount.authentication.recoverpassword.recover;

import com.commercetools.sunrise.test.WithPlayApplication;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.data.FormFactory;

import java.util.Map;

import static com.commercetools.sunrise.it.TestFixtures.provideSimpleApplicationBuilder;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DefaultRecoverPasswordFormData}.
 */
public class DefaultRecoverPasswordFormDataTest extends WithPlayApplication {
    private Form<DefaultRecoverPasswordFormData> form;

    @Override
    protected Application provideApplication() {
        final Application app = provideSimpleApplicationBuilder()
                .build();
        return app;
    }

    @Before
    public void setup() {
        final FormFactory formFactory = app.injector().instanceOf(FormFactory.class);
        form = formFactory.form(DefaultRecoverPasswordFormData.class);
    }

    @Test
    public void shouldAcceptValidFormData() {
        final Form<DefaultRecoverPasswordFormData> validFormData =
                form.bind(recoverPasswordFormData("test@example.com"));
        assertThat(validFormData.errors()).isEmpty();
    }

    @Test
    public void shouldReportInvalidEmail() {
        final Form<DefaultRecoverPasswordFormData> invalidEmail =
                form.bind(recoverPasswordFormData("test@"));
        assertThat(invalidEmail.errors()).hasSize(1);
        assertThat(invalidEmail.error("email")).isNotNull();
    }

    @Test
    public void shouldReportMissingEmail() {
        final Form<DefaultRecoverPasswordFormData> emailMissing =
                form.bind(recoverPasswordFormData(""));
        assertThat(emailMissing.errors()).hasSize(1);
        assertThat(emailMissing.error("email")).isNotNull();
    }

    private Map<String, String> recoverPasswordFormData(final String email) {
        return ImmutableMap.of("email", email);
    }
}
