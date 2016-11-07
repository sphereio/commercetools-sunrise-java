package com.commercetools.sunrise.common.sessions;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Optional;

public abstract class AbstractSerializedObjectInSession<T> extends AbstractObjectInSession<T> {

    @Inject
    protected AbstractSerializedObjectInSession(final Http.Session session) {
        super(session);
    }

    @Override
    protected final <U> Optional<U> findObjectByKey(final String sessionKey, final Class<U> clazz) {
        return findValueByKey(sessionKey)
                .flatMap(valueAsJson -> {
                    try {
                        final JsonNode jsonNode = Json.parse(valueAsJson);
                        final U value = Json.fromJson(jsonNode, clazz);
                        return Optional.of(value);
                    } catch (RuntimeException e) {
                        logger.error("Could not parse value in session key \"{}\"", sessionKey, e);
                        return Optional.empty();
                    }
                });
    }

    @Override
    protected final <U> void overwriteObjectByKey(final String key, final U value) {
        final JsonNode jsonNode = Json.toJson(value);
        final String valueAsJson = Json.stringify(jsonNode);
        overwriteValueByKey(key, valueAsJson);
    }

    @Override
    protected void removeObjectByKey(final String sessionKey) {
        removeValueByKey(sessionKey);
    }
}
