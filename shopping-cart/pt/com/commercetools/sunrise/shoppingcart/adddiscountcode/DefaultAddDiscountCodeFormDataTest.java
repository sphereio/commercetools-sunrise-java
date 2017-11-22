package com.commercetools.sunrise.shoppingcart.adddiscountcode;

import io.sphere.sdk.projects.Project;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.data.FormFactory;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static play.inject.Bindings.bind;

/**
 * Unit tests for {@link DefaultAddDiscountCodeFormData}.
 */
public class DefaultAddDiscountCodeFormDataTest extends WithApplication {

    private Form<DefaultAddDiscountCodeFormData> form;

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
        form = formFactory.form(DefaultAddDiscountCodeFormData.class);
    }

    @Test
    public void shouldAcceptValidFormData() {
        final Form<DefaultAddDiscountCodeFormData> validFormData =
                form.bind(addDiscountCodeFormData("SUNNY"));
        assertThat(validFormData.errors()).isEmpty();
    }

    @Test
    public void shouldReportEmptyDiscountCodeFormData() {
        final Form<DefaultAddDiscountCodeFormData> validFormData =
                form.bind(addDiscountCodeFormData(""));
        assertThat(validFormData.errors()).hasSize(1);
        assertThat(validFormData.error("code")).isNotNull();
    }

    @Test
    public void shouldReportNullDiscountCode() {
        final Form<DefaultAddDiscountCodeFormData> validFormData =
                form.bind(addDiscountCodeFormData(null));
        assertThat(validFormData.errors()).hasSize(1);
        assertThat(validFormData.error("code")).isNotNull();
    }

    private Map<String, String> addDiscountCodeFormData(final String code) {
        final Map<String, String> formData = new HashMap<>();
        formData.put("code", code);
        return formData;
    }
}
