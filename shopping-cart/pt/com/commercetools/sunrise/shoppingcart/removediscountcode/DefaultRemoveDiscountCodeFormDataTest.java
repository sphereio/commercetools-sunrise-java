package com.commercetools.sunrise.shoppingcart.removediscountcode;

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
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static play.inject.Bindings.bind;

/**
 * Unit tests for {@link DefaultRemoveDiscountCodeFormData}.
 */
public class DefaultRemoveDiscountCodeFormDataTest extends WithApplication {
    private Form<DefaultRemoveDiscountCodeFormData> form;

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
        form = formFactory.form(DefaultRemoveDiscountCodeFormData.class);
    }

    @Test
    public void shouldAcceptValidFormData() {
        final Form<DefaultRemoveDiscountCodeFormData> validFormData =
                form.bind(removeDiscountCodeFormData(UUID.randomUUID().toString()));
        assertThat(validFormData.errors()).isEmpty();
    }

    @Test
    public void shouldReportEmptyDiscountCodeIdFormData() {
        final Form<DefaultRemoveDiscountCodeFormData> validFormData =
                form.bind(removeDiscountCodeFormData(""));
        assertThat(validFormData.errors()).hasSize(1);
        assertThat(validFormData.error("discountCodeId")).isNotNull();
    }

    private Map<String, String> removeDiscountCodeFormData(final String discountCodeId) {
        return ImmutableMap.of("discountCodeId", discountCodeId);
    }
}
