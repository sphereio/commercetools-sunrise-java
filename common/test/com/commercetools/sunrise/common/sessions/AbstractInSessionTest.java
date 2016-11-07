package com.commercetools.sunrise.common.sessions;

import org.junit.Test;
import play.mvc.Http;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractInSessionTest {

    @Test
    public void worksForRegularWorkflow() throws Exception {
        final Http.Session session = new Http.Session(singletonMap("some-key", "some-value"));
        final TestableInSession inSession = new TestableInSession(session);

        assertThat(inSession.findValue())
                .as("Finds session value")
                .contains("some-value");

        inSession.overwrite("some-other-value");
        assertThat(inSession.findValue())
                .as("Overwrites session value")
                .contains("some-other-value");

        inSession.remove();
        assertThat(inSession.findValue())
                .as("Removes session value")
                .isEmpty();
    }

    @Test
    public void worksWithDerivedValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());
        final TestableInSession inSession = new TestableInSession(session);

        assertThat(inSession.findOtherValue())
                .as("Derived value is not in session")
                .isEmpty();

        inSession.overwrite("some-value");
        assertThat(inSession.findOtherValue())
                .as("Overwrites derived session value")
                .contains("10");

        inSession.remove();
        assertThat(inSession.findOtherValue())
                .as("Removes derived session value")
                .isEmpty();
    }

    @Test
    public void removesFromSessionOnNullValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());
        final TestableInSession inSession = new TestableInSession(session);

        inSession.overwrite("some-value");

        assertThat(inSession.findValue())
                .as("Finds session value")
                .contains("some-value");

        assertThat(inSession.findOtherValue())
                .as("Finds derived session value")
                .contains("10");

        inSession.overwrite(null);

        assertThat(inSession.findValue())
                .as("Removes session value on null")
                .isEmpty();

        assertThat(inSession.findOtherValue())
                .as("Removes derived session value on null")
                .isEmpty();
    }

    @Test
    public void usesUpToDateSession() throws Exception {
        final Http.Session session = new Http.Session(singletonMap("some-key", "some-value"));
        final TestableInSession inSession1 = new TestableInSession(session);
        final TestableInSession inSession2 = new TestableInSession(session);

        assertThat(inSession1.findValue()).contains("some-value");
        inSession2.remove();
        assertThat(inSession1.findValue()).isEmpty();
        inSession2.overwrite("some-other-value");
        assertThat(inSession1.findValue()).contains("some-other-value");
    }

    private static class TestableInSession extends AbstractInSession<String> {

        public TestableInSession(final Http.Session session) {
            super(session);
        }

        Optional<String> findValue() {
            return findValueByKey("some-key");
        }

        Optional<String> findOtherValue() {
            return findValueByKey("some-other-key");
        }

        @Override
        protected void overwriteRelatedValuesInSession(@NotNull final String value) {
            overwriteValueByKey("some-key", value);
            overwriteValueByKey("some-other-key", String.valueOf(value.length())); // size of value
        }

        @Override
        protected void removeRelatedValuesFromSession() {
            removeValueByKey("some-key");
            removeValueByKey("some-other-key");
        }
    }
}
