package com.commercetools.sunrise.common.sessions;

import io.sphere.sdk.models.Base;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractSerializedObjectInSessionTest {

    @Test
    public void worksForRegularWorkflow() throws Exception {
        final SomeObject someObject = new SomeObject("hello", 2);
        final SomeObject someOtherObject = new SomeObject("world", 4);

        final Http.Session session = new Http.Session(singletonMap("some-key", Json.stringify(Json.toJson(someObject))));
        final TestableSerializedObjectInSession inSession = new TestableSerializedObjectInSession(session);


        assertThat(inSession.findValue())
                .as("Finds session value")
                .contains(someObject);

        inSession.overwrite(someOtherObject);
        assertThat(inSession.findValue())
                .as("Overwrites session value")
                .contains(someOtherObject);

        inSession.remove();
        assertThat(inSession.findValue())
                .as("Removes session value")
                .isEmpty();
    }

    @Test
    public void worksWithDerivedValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());
        final TestableSerializedObjectInSession inSession = new TestableSerializedObjectInSession(session);

        assertThat(inSession.findOtherValue())
                .as("Derived value is not in session")
                .isEmpty();

        final SomeObject someObject = new SomeObject("hello", 2);
        inSession.overwrite(someObject);
        assertThat(inSession.findOtherValue())
                .as("Overwrites derived session value")
                .contains("hello");

        inSession.remove();
        assertThat(inSession.findOtherValue())
                .as("Removes derived session value")
                .isEmpty();
    }

    private static class TestableSerializedObjectInSession extends AbstractSerializedObjectInSession<SomeObject> {

        public TestableSerializedObjectInSession(final Http.Session session) {
            super(session);
        }

        Optional<SomeObject> findValue() {
            return findObjectByKey("some-key", SomeObject.class);
        }

        Optional<String> findOtherValue() {
            return findValueByKey("some-other-key");
        }

        @Override
        protected void overwriteRelatedValuesInSession(@NotNull final SomeObject value) {
            overwriteObjectByKey("some-key", value);
            overwriteValueByKey("some-other-key", value.text);
        }

        @Override
        protected void removeRelatedValuesFromSession() {
            removeValueByKey("some-key");
            removeValueByKey("some-other-key");
        }
    }

    private static class SomeObject extends Base {

        public String text;
        public int amount;

        public SomeObject() {
        }

        public SomeObject(final String text, final int amount) {
            this.text = text;
            this.amount = amount;
        }
    }
}
