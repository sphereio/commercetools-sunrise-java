package com.commercetools.sunrise.myaccount.authentication.recoverpassword.reset;

import com.google.common.collect.ImmutableMap;
import io.sphere.sdk.projects.Project;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.data.FormFactory;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static play.inject.Bindings.bind;

/**
 * Unit tests for {@link DefaultResetPasswordFormData}.
 */
public class DefaultResetPasswordFormDataTest extends WithApplication {

    private Form<DefaultResetPasswordFormData> form;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(Project.class).toInstance(mock(Project.class)))
                .configure("play.i18n.langs", singletonList("en"))
                .build();
    }

    @Before
    public void setup() {
        final FormFactory formFactory = app.injector().instanceOf(FormFactory.class);
        form = formFactory.form(DefaultResetPasswordFormData.class);
    }

    @Test
    public void shouldAcceptValidFormData() {
        final Form<DefaultResetPasswordFormData> validResetPasswordFormData =
                form.bind(resetPasswordFormData("password", "password"));
        assertThat(validResetPasswordFormData.errors()).isEmpty();
    }

    @Test
    public void shouldReportThatPasswordsAreNotEqual() {
        final Form<DefaultResetPasswordFormData> passwordsNotEqual =
                form.bind(resetPasswordFormData("password", "p"));
        assertThat(passwordsNotEqual.errors()).hasSize(1);
        assertThat(passwordsNotEqual.error(""))
                .describedAs("Reported with an empty key because it's a cross field validation")
                .isNotNull();
    }

    @Test
    public void shouldReportThatNewPasswordIsRequired() {
        final Form<DefaultResetPasswordFormData> newPasswordIsMissing =
                form.bind(resetPasswordFormData("", "password"));
        assertThat(newPasswordIsMissing.errors()).hasSize(1);
        assertThat(newPasswordIsMissing.error("newPassword")).isNotNull();
    }

    @Test
    public void shouldReportThatConfirmPasswordIsRequired() {
        final Form<DefaultResetPasswordFormData> confirmPasswordIsMissing =
                form.bind(resetPasswordFormData("password", ""));
        assertThat(confirmPasswordIsMissing.errors()).hasSize(1);
        assertThat(confirmPasswordIsMissing.error("confirmPassword")).isNotNull();
    }

    private Map<String, String> resetPasswordFormData(final String newPassword, final String confirmPassword) {
        return ImmutableMap.of("newPassword", newPassword, "confirmPassword", confirmPassword);
    }
}
