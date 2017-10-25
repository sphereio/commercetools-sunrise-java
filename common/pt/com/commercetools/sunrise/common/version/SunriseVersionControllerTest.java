package com.commercetools.sunrise.common.version;

import com.commercetools.sunrise.test.WithPlayApplication;
import org.junit.Test;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.contentAsString;

public class SunriseVersionControllerTest extends WithPlayApplication {

    @Test
    public void showsVersion() throws Exception {
        final Result result = new VersionControllerTest(app).show();
        assertThat(result.status()).isEqualTo(Http.Status.OK);
        assertThat(result.contentType()).contains(Http.MimeTypes.JSON);
        assertThat(contentAsString(result)).contains("version").contains("build");
    }

    private static class VersionControllerTest extends SunriseVersionController {

        VersionControllerTest(final Application application) {
            super(application);
        }
    }
}