package com.commercetools.sunrise.framework.template.engine.handlebars;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.framework.template.engine.TemplateContext;
import com.commercetools.sunrise.framework.template.engine.TemplateEngine;
import com.commercetools.sunrise.framework.viewmodels.PageData;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import io.sphere.sdk.projects.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;

@RunWith(MockitoJUnitRunner.class)
public class HandlebarsTemplateEngineCmsTest extends WithApplication {

    private static final List<TemplateLoader> TEMPLATE_LOADERS = singletonList(new ClassPathTemplateLoader("/handlebars/cms"));

    @Mock
    private CmsPage cmsPageThatReturnsSomething;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(Project.class).toInstance(mock(Project.class)))
                .configure("play.i18n.langs", singletonList("en"))
                .build();
    }

    @Before
    public void setUp() throws Exception {
        when(cmsPageThatReturnsSomething.fieldOrEmpty("foo.bar")).thenReturn("something");
    }

    @Test
    public void resolvesMessage() throws Exception {
        testTemplate("simple", html -> assertThat(html).contains("something"));
    }

    @Test
    public void fieldNotFound() throws Exception {
        testTemplate("missingField", html -> assertThat(html).isEmpty());
    }

    @Test
    public void bundleNotFound() throws Exception {
        testTemplate("missingBundle", html -> assertThat(html).isEmpty());
    }

    private void testTemplate(final String templateName, final Consumer<String> test) {
        final TemplateContext templateContext = new TemplateContext(new PageData(), cmsPageThatReturnsSomething);
        final Handlebars handlebars = app.injector().instanceOf(HandlebarsFactory.class).create(TEMPLATE_LOADERS);
        final HandlebarsContextFactory handlebarsContextFactory = app.injector().instanceOf(HandlebarsContextFactory.class);
        final TemplateEngine templateEngine = HandlebarsTemplateEngine.of(handlebars, handlebarsContextFactory);
        final String html = templateEngine.render(templateName, templateContext);
        test.accept(html);
    }
}
