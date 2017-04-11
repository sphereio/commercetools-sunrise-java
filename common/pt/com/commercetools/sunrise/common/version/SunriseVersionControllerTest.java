package com.commercetools.sunrise.common.version;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;
import org.junit.Test;
import org.slf4j.Logger;
import play.Application;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;

public class SunriseVersionControllerTest extends WithApplication {

    @Test
    public void showsVersion() throws Exception {
        final Result result = new VersionControllerTest(app).show();
        assertThat(result.status()).isEqualTo(Http.Status.OK);
        assertThat(result.contentType()).contains(Http.MimeTypes.JSON);
        assertThat(contentAsString(result)).contains("version").contains("build");

        final Logger logger = org.slf4j.LoggerFactory.getLogger("play");
        LoggerContext loggerContext = ((ch.qos.logback.classic.Logger)logger).getLoggerContext();
        URL mainURL = ConfigurationWatchListUtil.getMainWatchURL(loggerContext);
        System.out.println(mainURL);
        logger.debug("PRinting stufff test");
    }

    private static class VersionControllerTest extends SunriseVersionController {

        VersionControllerTest(final Application application) {
            super(application);
        }
    }
}