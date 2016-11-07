package com.commercetools.sunrise.common.sessions;

import io.sphere.sdk.models.Base;
import org.junit.Test;
import play.Configuration;
import play.cache.CacheApi;
import play.mvc.Http;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractCachableObjectInSessionTest {

    @Test
    public void worksForRegularWorkflow() throws Exception {
        final SomeObject someObject = new SomeObject("hello", 2);
        final SomeObject someOtherObject = new SomeObject("world", 4);

        final Http.Session session = new Http.Session(singletonMap("some-key", "some-cache-key"));
        final Map<String, Object> cache = singletonMap("some-cache-key", someObject);
        final TestableCachableObjectInSession inSession = new TestableCachableObjectInSession(session, cache);

        assertThat(inSession.findValue())
                .as("Finds session object")
                .contains(someObject);

        inSession.overwrite(someOtherObject);
        assertThat(inSession.findValue())
                .as("Overwrites session object")
                .contains(someOtherObject);

        inSession.remove();
        assertThat(inSession.findValue())
                .as("Removes session value")
                .isEmpty();
    }

    @Test
    public void worksWithDerivedValues() throws Exception {
        final Http.Session session = new Http.Session(emptyMap());
        final Map<String, Object> cache = emptyMap();
        final TestableCachableObjectInSession inSession = new TestableCachableObjectInSession(session, cache);

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

    private static class TestableCachableObjectInSession extends AbstractCachableObjectInSession<SomeObject> {

        public TestableCachableObjectInSession(final Http.Session session, final Map<String, Object> initialCache) {
            super(session, new Configuration(emptyMap()), buildCache(initialCache));
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

        private static CacheApi buildCache(final Map<String, Object> initialCache) {
            return new CacheApi() {

                final Map<String, Object> cache = new HashMap<>(initialCache);

                @Override
                public <T> T get(final String key) {
                    return (T) cache.get(key);
                }

                @Override
                public <T> T getOrElse(final String key, final Callable<T> block, final int expiration) {
                    return null;
                }

                @Override
                public <T> T getOrElse(final String key, final Callable<T> block) {
                    return null;
                }

                @Override
                public void set(final String key, final Object value, final int expiration) {

                }

                @Override
                public void set(final String key, final Object value) {
                    cache.put(key, value);
                }

                @Override
                public void remove(final String key) {
                    cache.remove(key);
                }
            };
        }

    }

    private static class SomeObject extends Base {

        private final String text;
        private final int amount;

        public SomeObject(final String text, final int amount) {
            this.text = text;
            this.amount = amount;
        }
    }
}
