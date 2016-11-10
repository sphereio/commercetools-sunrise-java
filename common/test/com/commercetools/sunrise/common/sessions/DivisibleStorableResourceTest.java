package com.commercetools.sunrise.common.sessions;

import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class DivisibleStorableResourceTest {

    @Test
    public void overwritesRelatedValues() throws Exception {
        testInitializedSession(stringInSession -> {
            stringInSession.write("some-other-value");
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
            stringInSession.write(null);
            assertThat(stringInSession.findValue()).isEmpty();
            assertThat(stringInSession.findOtherValue()).isEmpty();
        });
    }

    private void testInitializedSession(final Consumer<TestableStringInStorableDataFromResourceStore> test) {
        final TestableStringInStorableDataFromResourceStore stringInSession = new TestableStringInStorableDataFromResourceStore();
        stringInSession.write("some-value");

        assertThat(stringInSession.findValue())
                .as("Direct value is correctly initialized")
                .contains("some-value");
        assertThat(stringInSession.findOtherValue())
                .as("Derived values is correctly initialized")
                .contains("10");

        test.accept(stringInSession);
    }

    private static class TestableStringInStorableDataFromResourceStore extends StorableDataFromResource<String> {

        private final Map<String, String> session;

        TestableStringInStorableDataFromResourceStore() {
            this.session = new HashMap<>();
        }

        Optional<String> findValue() {
            return Optional.ofNullable(session.get("some-key"));
        }

        Optional<String> findOtherValue() {
            return Optional.ofNullable(session.get("some-other-key"));
        }

        @Override
        protected void writeAssociatedData(@NotNull final String value) {
            session.put("some-key", value);
            session.put("some-other-key", String.valueOf(value.length())); // size of value
        }

        @Override
        protected void removeAssociatedData() {
            session.remove("some-key");
            session.remove("some-other-key");
        }
    }
}
