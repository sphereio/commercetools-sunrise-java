package com.commercetools.sunrise.common.sessions;

import org.junit.Test;
import play.mvc.Http;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractSessionHandlerTest {

    private static final String SOME_KEY = "some-key";
    private static final Http.Session SESSION = new Http.Session(singletonMap(SOME_KEY, "some-value"));
    private static final TestableSessionHandler HANDLER = new TestableSessionHandler();

    @Test
    public void worksForRegularWorkflow() throws Exception {
        assertThat(HANDLER.findValue(SESSION))
                .as("Finds session value")
                .contains("some-value");

        HANDLER.overwriteInSession(SESSION, "other-value");
        assertThat(HANDLER.findValue(SESSION))
                .as("Overwrites session value")
                .contains("other-value");

        HANDLER.removeFromSession(SESSION);
        assertThat(HANDLER.findValue(SESSION))
                .as("Removes session value")
                .isEmpty();
    }

    private static class TestableSessionHandler extends AbstractSessionHandler<String> {

        Optional<String> findValue(final Http.Session session) {
            return findValueByKey(session, SOME_KEY);
        }

        @Override
        protected void overwriteRelatedValuesInSession(final Http.Session session, @NotNull final String value) {
            overwriteValueByKey(session, SOME_KEY, value);
        }

        @Override
        protected void removeRelatedValuesFromSession(final Http.Session session) {
            removeValueByKey(session, SOME_KEY);
        }
    }
}
