package com.commercetools.sunrise.common.sessions;

import org.junit.Test;
import play.mvc.Http;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractSessionHandlerTest {

    private static final TestableSessionHandler HANDLER = new TestableSessionHandler();

    @Test
    public void worksForRegularWorkflow() throws Exception {
        final Http.Session session = new Http.Session(singletonMap("some-key", "some-value"));

        assertThat(HANDLER.findValue(session))
                .as("Finds session value")
                .contains("some-value");

        HANDLER.overwriteInSession(session, "some-other-value");
        assertThat(HANDLER.findValue(session))
                .as("Overwrites session value")
                .contains("some-other-value");

        HANDLER.removeFromSession(session);
        assertThat(HANDLER.findValue(session))
                .as("Removes session value")
                .isEmpty();
    }

    @Test
    public void worksWithDerivedValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());

        assertThat(HANDLER.findOtherValue(session))
                .as("Derived value is not in session")
                .isEmpty();

        HANDLER.overwriteInSession(session, "some-value");
        assertThat(HANDLER.findOtherValue(session))
                .as("Overwrites derived session value")
                .contains("10");

        HANDLER.removeFromSession(session);
        assertThat(HANDLER.findOtherValue(session))
                .as("Removes derived session value")
                .isEmpty();
    }

    @Test
    public void removesFromSessionOnNullValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());

        HANDLER.overwriteInSession(session, "some-value");

        assertThat(HANDLER.findValue(session))
                .as("Finds session value")
                .contains("some-value");

        assertThat(HANDLER.findOtherValue(session))
                .as("Finds derived session value")
                .contains("10");

        HANDLER.overwriteInSession(session, null);

        assertThat(HANDLER.findValue(session))
                .as("Removes session value on null")
                .isEmpty();

        assertThat(HANDLER.findOtherValue(session))
                .as("Removes derived session value on null")
                .isEmpty();
    }

    private static class TestableSessionHandler extends AbstractSessionHandler<String> {

        Optional<String> findValue(final Http.Session session) {
            return findValueByKey(session, "some-key");
        }

        Optional<String> findOtherValue(final Http.Session session) {
            return findValueByKey(session, "some-other-key");
        }

        @Override
        protected void overwriteRelatedValuesInSession(final Http.Session session, @NotNull final String value) {
            overwriteValueByKey(session, "some-key", value);
            overwriteValueByKey(session, "some-other-key", String.valueOf(value.length())); // size of value
        }

        @Override
        protected void removeRelatedValuesFromSession(final Http.Session session) {
            removeValueByKey(session, "some-key");
            removeValueByKey(session, "some-other-key");
        }
    }
}
