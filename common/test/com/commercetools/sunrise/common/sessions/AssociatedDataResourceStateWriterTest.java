package com.commercetools.sunrise.common.sessions;

import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AssociatedDataResourceStateWriterTest {

    @Test
    public void overwritesRelatedValues() throws Exception {
        testInitializedSession(stringInSession -> {
            stringInSession.overwrite("some-other-value");
            assertThat(stringInSession.findValue()).contains("some-other-value");
            assertThat(stringInSession.findOtherValue()).contains("16");
        });
    }

    @Test
    public void removesRelatedValues() throws Exception {
        testInitializedSession(stringInSession -> {
            stringInSession.remove();
            assertThat(stringInSession.findValue()).isEmpty();
            assertThat(stringInSession.findOtherValue()).isEmpty();
        });
    }

    @Test
    public void removesRelatedValuesOnNullParameter() throws Exception {
        testInitializedSession(stringInSession -> {
            stringInSession.overwrite(null);
            assertThat(stringInSession.findValue()).isEmpty();
            assertThat(stringInSession.findOtherValue()).isEmpty();
        });
    }

    private void testInitializedSession(final Consumer<TestableStringInResourceState> test) {
        final TestableStringInResourceState stringInSession = new TestableStringInResourceState();
        stringInSession.overwrite("some-value");

        assertThat(stringInSession.findValue())
                .as("Direct value is correctly initialized")
                .contains("some-value");
        assertThat(stringInSession.findOtherValue())
                .as("Derived values is correctly initialized")
                .contains("10");

        test.accept(stringInSession);
    }

    private static class TestableStringInResourceState extends AssociatedDataResourceStateWriter<String> {

        private final Map<String, String> session;

        TestableStringInResourceState() {
            this.session = new HashMap<>();
        }

        Optional<String> findValue() {
            return Optional.ofNullable(session.get("some-key"));
        }

        Optional<String> findOtherValue() {
            return Optional.ofNullable(session.get("some-other-key"));
        }

        @Override
        protected void overwriteAssociatedDataInSession(@NotNull final String value) {
            session.put("some-key", value);
            session.put("some-other-key", String.valueOf(value.length())); // size of value
        }

        @Override
        protected void removeAssociatedDataFromSession() {
            session.remove("some-key");
            session.remove("some-other-key");
        }
    }
}
